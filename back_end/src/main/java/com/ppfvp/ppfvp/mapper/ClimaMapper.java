package com.ppfvp.ppfvp.mapper;

import com.ppfvp.ppfvp.dto.*;
import com.ppfvp.ppfvp.model.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ClimaMapper {

    public ClimaModel toModel(ClimaResponse response) {
        if (response == null) return null;

        return ClimaModel.builder()
                .name(response.name())
                .base(response.base())
                .visibility(response.visibility())
                .dt(response.dt())
                .timezone(response.timezone())
                .cod(response.cod())
                .coord(mapCoord(response.coord()))
                .main(mapMain(response.main()))
                .clouds(mapClouds(response.clouds()))
                .sys(mapSys(response.sys()))
                .wind(mapWind(response.wind()))
                .weather(mapWeatherList(response.weather()))
                .build();
    }

    public ClimaResponse toResponse(ClimaModel model) {
        if (model == null) return null;

        return new ClimaResponse(
                toCoordResponse(model.getCoord()),
                toWeatherResponseList(model.getWeather()),
                model.getBase(),
                toMainResponse(model.getMain()),
                model.getVisibility(),
                toWindResponse(model.getWind()),
                toCloudsResponse(model.getClouds()),
                model.getDt(),
                toSysResponse(model.getSys()),
                model.getTimezone(),
                model.getId(),
                model.getName(),
                model.getCod()
        );
    }

    public void updateModelFromResponse(ClimaResponse response, ClimaModel modelToUpdate) {
        if (response == null || modelToUpdate == null) return;

        modelToUpdate.setName(response.name());
        modelToUpdate.setBase(response.base());
        modelToUpdate.setVisibility(response.visibility());
        modelToUpdate.setDt(response.dt());
        modelToUpdate.setTimezone(response.timezone());
        modelToUpdate.setCod(response.cod());

        modelToUpdate.setCoord(mapCoord(response.coord()));
        modelToUpdate.setMain(mapMain(response.main()));
        modelToUpdate.setClouds(mapClouds(response.clouds()));
        modelToUpdate.setSys(mapSys(response.sys()));
        modelToUpdate.setWind(mapWind(response.wind()));

        atualizarListaDeWeather(modelToUpdate, response.weather());
    }

    private void atualizarListaDeWeather(ClimaModel modelToUpdate, List<WeatherResponse> newWeathers) {
        List<Weather> weathersConvertidos = mapWeatherList(newWeathers);
        if (modelToUpdate.getWeather() != null) {
            modelToUpdate.getWeather().clear();
            if (weathersConvertidos != null) {
                modelToUpdate.getWeather().addAll(weathersConvertidos);
            }
        } else {
            modelToUpdate.setWeather(weathersConvertidos);
        }
    }

    // --- Mapeadores Auxiliares ---
    private Coord mapCoord(CoordResponse r) { return r == null ? null : Coord.builder().lon(r.lon()).lat(r.lat()).build(); }
    private Main mapMain(MainResponse r) { return r == null ? null : Main.builder().temp(r.temp()).feelsLike(r.feels_like()).tempMin(r.temp_min()).tempMax(r.temp_max()).pressure(r.pressure()).humidity(r.humidity()).seaLevel(r.sea_level()).grndLevel(r.grnd_level()).build(); }
    private Clouds mapClouds(CloudsResponse r) { return r == null ? null : Clouds.builder().all(r.all()).build(); }
    private Sys mapSys(SysResponse r) { return r == null ? null : Sys.builder().country(r.country()).sunrise(r.sunrise()).sunset(r.sunset()).build(); }
    private Wind mapWind(WindResponse r) { return r == null ? null : Wind.builder().speed(r.speed()).deg(r.deg()).gust(r.gust()).build(); }
    private Weather mapWeather(WeatherResponse r) { return r == null ? null : Weather.builder().main(r.main()).description(r.description()).icon(r.icon()).build(); }
    private List<Weather> mapWeatherList(List<WeatherResponse> responses) { return responses == null ? Collections.emptyList() : responses.stream().map(this::mapWeather).toList(); }

    private CoordResponse toCoordResponse(Coord c) { return c == null ? null : new CoordResponse(c.getLon(), c.getLat()); }
    private MainResponse toMainResponse(Main m) { return m == null ? null : new MainResponse(m.getTemp(), m.getFeelsLike(), m.getTempMin(), m.getTempMax(), m.getPressure(), m.getHumidity(), m.getSeaLevel(), m.getGrndLevel()); }
    private CloudsResponse toCloudsResponse(Clouds c) { return c == null ? null : new CloudsResponse(c.getAll()); }
    private SysResponse toSysResponse(Sys s) { return s == null ? null : new SysResponse(s.getCountry(), s.getSunrise(), s.getSunset()); }
    private WindResponse toWindResponse(Wind w) { return w == null ? null : new WindResponse(w.getSpeed(), w.getDeg(), w.getGust()); }
    private WeatherResponse toWeatherResponse(Weather w) { return w == null ? null : new WeatherResponse(null, w.getMain(), w.getDescription(), w.getIcon()); }
    private List<WeatherResponse> toWeatherResponseList(List<Weather> weathers) { return weathers == null ? Collections.emptyList() : weathers.stream().map(this::toWeatherResponse).toList(); }
}