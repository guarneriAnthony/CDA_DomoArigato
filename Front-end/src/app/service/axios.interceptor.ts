import axios from 'axios';
import { CookieManagerService } from './cookie-manager.service';

export const createAxiosInstance = (cookieManagerService: CookieManagerService) => {
  const axiosInstance = axios.create({ baseURL: 'http://localhost:8080' });

  axiosInstance.interceptors.request.use(
    (config) => {
      const token = cookieManagerService.getToken();
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  return axiosInstance;
};
