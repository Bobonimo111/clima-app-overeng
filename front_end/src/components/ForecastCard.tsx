import type { ForecastData } from '../types';
import ensolaradoIcon from '../assets/Ensolarado.png';
import nubladoIcon from '../assets/nublado.png';
import parcialmenteNubladoIcon from '../assets/Parcialmente nublado.png';
import chuvaIcon from '../assets/Chuva.png';
import chuvaLeveIcon from '../assets/Chuva leve.png';

interface ForecastCardProps extends ForecastData {
  onClick?: () => void;
}

export const ForecastCard = ({ day, condition, rainProbability, temperature, isActive, onClick }: ForecastCardProps) => {
  const getIcon = (cond: ForecastData['condition']) => {
    switch(cond) {
      case 'sunny': return ensolaradoIcon;
      case 'cloudy': return nubladoIcon;
      case 'partly-cloudy': return parcialmenteNubladoIcon;
      case 'rain': return chuvaIcon;
      case 'light-rain': return chuvaLeveIcon;
      default: return parcialmenteNubladoIcon;
    }
  };

  const baseClasses = "flex flex-col items-center justify-between rounded-[32px] w-[72px] h-[140px] py-4 border shrink-0 transition-all cursor-pointer";
  const activeClasses = isActive ? "bg-[#111111] border-[#6D3DB3]" : "bg-white/10 border-white/20";

  return (
    <div className={`${baseClasses} ${activeClasses}`} onClick={onClick}>
      <span className="text-white text-[11px] font-semibold">{day}</span>
      <div className="flex flex-col items-center mt-1">
        <img src={getIcon(condition)} alt={condition} className="w-8 h-8 object-contain" />
      </div>
      <span className="text-white text-lg font-normal">{temperature}°</span>
    </div>
  );
};