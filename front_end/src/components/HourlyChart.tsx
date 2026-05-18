import type { HourlyData } from '../types';
import ensolaradoIcon from '../assets/Ensolarado.png';
import nubladoIcon from '../assets/nublado.png';
import parcialmenteNubladoIcon from '../assets/Parcialmente nublado.png';
import chuvaIcon from '../assets/Chuva.png';
import chuvaLeveIcon from '../assets/Chuva leve.png';

export const HourlyChart = ({ data }: { data: HourlyData[] }) => {
  const getIcon = (cond: HourlyData['condition']) => {
    switch (cond) {
      case 'sunny': return ensolaradoIcon;
      case 'cloudy': return nubladoIcon;
      case 'partly-cloudy': return parcialmenteNubladoIcon;
      case 'rain': return chuvaIcon;
      case 'light-rain': return chuvaLeveIcon;
      default: return parcialmenteNubladoIcon;
    }
  };

  const ITEM_WIDTH = 64;
  const CHART_HEIGHT = 45;
  
  const minTemp = Math.min(...data.map((d) => d.temperature));
  const maxTemp = Math.max(...data.map((d) => d.temperature));
  const range = maxTemp - minTemp === 0 ? 1 : maxTemp - minTemp;

  const points = data.map((d, i) => {
    const x = i * ITEM_WIDTH + (ITEM_WIDTH / 2);
    const y = CHART_HEIGHT - ((d.temperature - minTemp) / range) * (CHART_HEIGHT - 20) - 10;
    return `${x},${y}`;
  });

  const pathData = `M ${points.join(' L ')}`;

  return (
    <div className="w-full overflow-x-auto flex [&::-webkit-scrollbar]:hidden [-ms-overflow-style:none] [scrollbar-width:none] mt-6 pb-2">
      <div 
        className="relative flex flex-col pt-2" 
        style={{ width: `${data.length * ITEM_WIDTH}px` }}
      >
        <div className="flex w-full mb-1">
          {data.map((d, i) => (
            <div key={i} className="flex justify-center text-white text-[15px] font-medium" style={{ width: `${ITEM_WIDTH}px` }}>
              {d.temperature}°
            </div>
          ))}
        </div>

        <svg width="100%" height={CHART_HEIGHT} className="overflow-visible">
          <path d={pathData} fill="none" stroke="#FCE928" strokeWidth="2" />
          {data.map((d, i) => {
            const x = i * ITEM_WIDTH + (ITEM_WIDTH / 2);
            const y = CHART_HEIGHT - ((d.temperature - minTemp) / range) * (CHART_HEIGHT - 20) - 10;
            return <circle key={i} cx={x} cy={y} r="3" fill="#FCE928" />;
          })}
        </svg>

        <div className="flex w-full mt-3">
          {data.map((d, i) => (
            <div key={i} className="flex flex-col items-center gap-2" style={{ width: `${ITEM_WIDTH}px` }}>
              <img src={getIcon(d.condition)} alt={d.condition} className="w-6 h-6 object-contain" />
              <span className="text-gray-400 text-[12px] font-medium">{d.time}</span>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};