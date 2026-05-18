import { ForecastCard } from './ForecastCard';
import { HourlyChart } from './HourlyChart';
import type { ForecastData } from '../types';

interface ForecastCarouselProps {
  forecast: ForecastData[];
  onSelectDay: (index: number) => void;
}

export const ForecastCarousel = ({ forecast, onSelectDay }: ForecastCarouselProps) => {
  const activeForecast = forecast.find(f => f.isActive) || forecast[0];

  return (
    <div className="flex flex-col w-full">
      <div className="flex gap-4 overflow-x-auto w-full pb-2 [&::-webkit-scrollbar]:hidden [-ms-overflow-style:none] [scrollbar-width:none]">
        {forecast.map((item, index) => (
          <ForecastCard 
            key={index} 
            {...item} 
            onClick={() => onSelectDay(index)} 
          />
        ))}
      </div>
      
      {activeForecast?.hourly && activeForecast.hourly.length > 0 && (
        <HourlyChart data={activeForecast.hourly} />
      )}
    </div>
  );
};