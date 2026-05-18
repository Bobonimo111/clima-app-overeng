import { useState, useEffect } from "react";
import { CurrentWeather } from "../components/CurrentWeather";
import { ForecastCarousel } from "../components/ForecastCarousel";
import { Header } from "../components/Header";
import { WeatherAlert } from "../components/WeatherAlert";
import { WeatherEffects } from "../components/WeatherEffects";
import { ErrorPopup } from "../components/ErrorPopup";
import { climaService } from "../services/climaService";
import type { WeatherData, ForecastData } from "../types";
import { getWeatherIcon } from "../utils/weatherUtils";

const mapearCondicao = (
  descricaoApi: string | undefined,
): WeatherData["condition"] => {
  if (!descricaoApi) return "sunny";
  const desc = descricaoApi.toLowerCase();

  if (desc.includes("chuva leve") || desc.includes("garoa") || desc.includes("drizzle") || desc.includes("light rain")) return "light-rain";
  if (desc.includes("chuva") || desc.includes("tempestade") || desc.includes("rain") || desc.includes("thunderstorm")) return "rain";
  if (desc.includes("nublado") || desc.includes("overcast") || desc.includes("broken clouds") || desc.includes("clouds")) return "cloudy";
  if (desc.includes("algumas nuvens") || desc.includes("nuvens dispersas") || desc.includes("scattered") || desc.includes("few clouds")) return "partly-cloudy";

  return "sunny";
};

export function HomePage() {
  const [weatherData, setWeatherData] = useState<WeatherData | null>(null);
  const [forecastData, setForecastData] = useState<ForecastData[]>([]);
  const [cidadeBusca, setCidadeBusca] = useState("Recife");
  const [showErrorPopup, setShowErrorPopup] = useState(false);
  const [selectedDayIndex, setSelectedDayIndex] = useState(0);

  useEffect(() => {
    const carregarDados = async () => {
      try {
        const climaDaApi = await climaService.buscarClimaAtual(cidadeBusca);

        if (!climaDaApi) {
          throw new Error("Cidade não encontrada");
        }

        const previsaoDaApi = await climaService.buscarPrevisaoDaSemana(cidadeBusca);

        let tempBruta = climaDaApi.temperatura;
        if (tempBruta > 100) {
          tempBruta = tempBruta - 273.15;
        }

        setWeatherData({
          city: climaDaApi.cidade,
          temperature: Math.round(tempBruta),
          condition: mapearCondicao(climaDaApi.descricao),
          icon: getWeatherIcon(climaDaApi.descricao),
        });

        const diasSemana = ["DOM", "SEG", "TER", "QUA", "QUI", "SEX", "SAB"];
        const previsaoAgrupada: Record<string, any> = {};

        for (const item of previsaoDaApi) {
          const dataString = String(item.dataHora).substring(0, 10);
          const timeString = String(item.dataHora).substring(11, 16);

          if (!previsaoAgrupada[dataString]) {
            const partes = dataString.split(/[-/]/);
            let dataObj = new Date();
            if (partes.length === 3) {
              if (partes[0].length === 4) {
                dataObj = new Date(`${partes[0]}-${partes[1]}-${partes[2]}T12:00:00`);
              } else {
                dataObj = new Date(`${partes[2]}-${partes[1]}-${partes[0]}T12:00:00`);
              }
            }
            previsaoAgrupada[dataString] = {
              day: diasSemana[dataObj.getDay()],
              hourly: []
            };
          }

          let tempPrev = item.temperatura;
          if (tempPrev > 100) tempPrev = tempPrev - 273.15;

          previsaoAgrupada[dataString].hourly.push({
            time: timeString,
            temperature: Math.round(tempPrev),
            condition: mapearCondicao(item.descricao),
            icon: getWeatherIcon(item.descricao)
          });
        }

        const previsaoParaTela: ForecastData[] = Object.values(previsaoAgrupada)
          .slice(0, 5)
          .map((grupo: any) => {
            const mainItem = grupo.hourly.find((h: any) => h.time.startsWith('12')) || grupo.hourly[0];
            
            return {
              day: grupo.day,
              temperature: mainItem.temperature,
              condition: mainItem.condition,
              icon: mainItem.icon,
              rainProbability: 0,
              hourly: grupo.hourly
            };
          });

        setForecastData(previsaoParaTela);
        setSelectedDayIndex(0);
        setShowErrorPopup(false);

      } catch (error) {
        setShowErrorPopup(true);
      }
    };

    carregarDados();
  }, [cidadeBusca]);

  const handleSearch = (novaCidade: string) => {
    if (novaCidade.trim() !== "") {
      setCidadeBusca(novaCidade);
    }
  };

  const currentForecast = forecastData.map((f, i) => ({
    ...f,
    isActive: i === selectedDayIndex
  }));

  return (
    <div className="min-h-screen max-w-md mx-auto bg-linear-to-b from-[#1c1c1c] via-[#0a0a0a] to-black flex flex-col pt-12 font-sans overflow-x-hidden relative text-white">
      
      <div className="absolute top-0 left-[-20%] w-[140%] h-[100px] bg-blue-500/10 rounded-full blur-[100px] pointer-events-none z-0 mix-blend-screen"></div>
      <div className="absolute top-[20%] right-[-30%] w-[75px] h-[75px] bg-purple-500/10 rounded-full blur-[120px] pointer-events-none z-0 mix-blend-screen"></div>

      {showErrorPopup && (
        <ErrorPopup cityName={cidadeBusca} onClose={() => setShowErrorPopup(false)} />
      )}

      <WeatherEffects condition={weatherData?.condition || 'sunny'} />

      <div className="flex flex-col items-center w-full shrink-0 relative z-10 px-4 mb-16">
        {weatherData && (
          <>
            <Header city={weatherData.city} onSearch={handleSearch} />
            <CurrentWeather
              temperature={weatherData.temperature}
              condition={weatherData.condition}
              icon={weatherData.icon}
            />
          </>
        )}
      </div>
      
      <div className="rounded-t-[40px] bg-white/5 backdrop-blur-xl border-t border-white/10 mt-auto pt-8 pb-10 relative z-10 w-full shadow-[0_-15px_40px_rgba(0,0,0,0.5)] flex-1 flex flex-col">
        <div className="flex flex-col w-full px-6">
          <p className="text-gray-400 font-medium text-sm tracking-wide mb-6">
            Previsão para os próximos 5 dias
          </p>
          
          {weatherData?.alert && <WeatherAlert message={weatherData.alert} />}
          
          {currentForecast.length > 0 && (
            <ForecastCarousel 
              forecast={currentForecast} 
              onSelectDay={setSelectedDayIndex}
            />
          )}
        </div>
      </div>
      
    </div>
  );
}