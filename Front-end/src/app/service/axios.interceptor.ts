import axios from 'axios';
import {CookieManagerService} from "./cookie-manager.service";
import {inject} from "@angular/core";
const axiosInstance = axios.create(
  {
    baseURL: 'http://localhost:8080',
  }
);

axiosInstance.interceptors.request.use(
  (config) => {

    const cookieManagerService = inject(CookieManagerService);
    const token = cookieManagerService.getToken();

    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
)

export default axiosInstance
