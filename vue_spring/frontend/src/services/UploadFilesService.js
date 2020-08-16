import http from "../http-common";

class UploadFilesService {
  upload(file, onUploadProgress) {
    const SERVER_URL = 'http://i3a511.p.ssafy.io:8301'
    let formData = new FormData();

    formData.append("file", file);

    return http.post(`${SERVER_URL}/api/wav/upload`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
        "jwt-auth-token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiLroZzqt7jsnbjthqDtgbAiLCJleHAiOjE1OTc1ODY2MzgsIlVzZXJEYXRhIjp7InVzZXJpZCI6MSwiZW1haWwiOiJhZG1pbkB0cmFuc2xhdGVseS5jb20iLCJuYW1lIjoiU2FtcGxlIiwicGFzc3dvcmQiOiJhYjEyMzQhQCIsInJlbWFpbnRpbWUiOjYwMCwiY3JlYXRlZGF0ZSI6eyJkYXlPZlllYXIiOjIyOSwiZGF5T2ZXZWVrIjoiU1VOREFZIiwibW9udGgiOiJBVUdVU1QiLCJkYXlPZk1vbnRoIjoxNiwieWVhciI6MjAyMCwibmFubyI6MCwibW9udGhWYWx1ZSI6OCwiaG91ciI6OSwibWludXRlIjo0Miwic2Vjb25kIjoxMywiY2hyb25vbG9neSI6eyJjYWxlbmRhclR5cGUiOiJpc284NjAxIiwiaWQiOiJJU08ifX0sInRva2VuIjpudWxsfSwic2Vjb25kIjoi642UIOuLtOqzoCDsi7bsnYDqsbAg7J6I7Ja0PyJ9.nHLnxbNTZevoUtcZLW1Spzwj0Z_jp_Ipf3oCWBOUFtI"
      },
      onUploadProgress
    });
  }

  getFiles() {
    return null
    // http.get("/api/files")
  }
}

export default new UploadFilesService();