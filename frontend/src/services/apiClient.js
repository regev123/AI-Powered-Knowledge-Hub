import axios from 'axios';

const apiClient = axios.create({
  baseURL: process.env.REACT_APP_API_URL || 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Variable to store current user for API headers
let currentUser = null;

/**
 * Sets the current user for API headers
 * @param {object} user - The current user object
 */
export const setUserProvider = (user) => {
  currentUser = user;

};

// Request interceptor to add user role and name to headers
apiClient.interceptors.request.use(
  (config) => {
    // Add user role and name to headers if current user is available
    if (currentUser) {
      if (currentUser.role) {
        config.headers['X-User-Role'] = currentUser.role;
      }
      if (currentUser.name) {
        config.headers['X-User-Name'] = currentUser.name;
      }
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default apiClient;

