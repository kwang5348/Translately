import axios from "axios";

export default axios.create({
  baseURL: "http://i3a511.p.ssafy.io:8301",
  headers: {
    "Content-type": "application/json",
  }
});