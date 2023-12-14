import { getUserinfo } from "./userinfo";

export const getJsonHeaders = () => {
  return {
    "Content-Type": "application/json",
    "Access-Control-Allow-Origin": "http://localhost:3000",
    "Access-Control-Allow-Methods": "GET, POST, PATCH, PUT, DELETE, OPTIONS",
    "Access-Control-Allow-Headers": "Origin, Content-Type, X-Auth-Token",
  };
};

export const getJsonHeadersWithJWT = () => {
  return {
    "Content-Type": "application/json",
    "Access-Control-Allow-Origin": "http://localhost:3000",
    "Access-Control-Allow-Methods": "GET, POST, PATCH, PUT, DELETE, OPTIONS",
    "Access-Control-Allow-Headers": "Origin, Content-Type, X-Auth-Token",
    "Authorization": "Bearer " + getUserinfo().jwt
  };
};
