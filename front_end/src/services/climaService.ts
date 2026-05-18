import { api } from './api';
import type { ClimaAtual, PrevisaoItem } from '../types';

export const climaService = {
  buscarClimaAtual: async (cidade: string): Promise<ClimaAtual> => {
    const response = await api.get<any>('/climas', {
      params: { city: cidade }
    });

    const data = response.data;

    return {
      cidade: data.name,
      temperatura: data.main?.temp ?? 0,
      sensacaoTermica: data.main?.feels_like ?? 0,
      descricao: data.weather?.[0]?.description ?? '',
      pais: data.sys?.country ?? 'BR'
    };
  },

  buscarPrevisaoDaSemana: async (cidade: string): Promise<PrevisaoItem[]> => {
    const response = await api.get<any>('/previsoes', {
      params: { city: cidade }
    });

    const data = response.data;
    let listaDePrevisoes: any[] = [];

    if (data && Array.isArray(data.previsoes)) {
      listaDePrevisoes = data.previsoes;
    } else if (Array.isArray(data)) {
      listaDePrevisoes = data;
    } else if (data && typeof data === 'object' && Array.isArray(data.list)) {
      listaDePrevisoes = data.list;
    }

    return listaDePrevisoes.map((item: any) => {
      let dt = new Date().toISOString();
      if (item.dataHora) dt = item.dataHora;
      else if (item.dt_txt) dt = item.dt_txt;
      else if (item.dt) dt = new Date(item.dt * 1000).toISOString();

      return {
        dataHora: dt,
        temperatura: item.temp ?? item.main?.temp ?? 0,
        descricao: item.descricao ?? item.weather?.[0]?.description ?? '',
        pais: data.cidade ?? data.city?.country ?? 'BR'
      };
    });
  }
};