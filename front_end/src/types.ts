export interface BackendWeatherResponse {
  name: string;
  main: {
    temp: number;
    feels_like: number;
    temp_min: number;
    temp_max: number;
    pressure: number;
    humidity: number;
  };
  weather: Array<{
    id: number;
    main: string;
    description: string;
    icon: string;
  }>;
  sys: {
    country: string;
    sunrise: number;
    sunset: number;
  };
}

export interface WeatherData {
  city: string;
  temperature: number;
  condition: 'sunny' | 'cloudy' | 'partly-cloudy' | 'rain' | 'light-rain';
  icon: string;
  alert?: string;
}

export interface HourlyData {
  time: string;
  temperature: number;
  condition: 'sunny' | 'cloudy' | 'partly-cloudy' | 'rain' | 'light-rain';
  icon: string;
}

export interface ForecastData {
  day: string;
  condition: 'sunny' | 'cloudy' | 'partly-cloudy' | 'rain' | 'light-rain';
  icon: string;
  rainProbability: number;
  temperature: number;
  isActive?: boolean;
  hourly?: HourlyData[];
}

export interface ClimaAtual {
  cidade: string;
  temperatura: number;
  sensacaoTermica: number;
  descricao: string;
  pais: string;
}

export interface PrevisaoItem {
  dataHora: string;
  temperatura: number;
  descricao: string;
  pais: string;
}