import http from "../http-common";

class UploadFilesService {
  upload(file, onUploadProgress) {
    const SERVER_URL = 'http://i3a511.p.ssafy.io:8399'
    let formData = new FormData();

    formData.append("file", file);

    return http.post(`${SERVER_URL}/api/wav/upload`, formData, {
      headers: {
        "Content-Type": "multipart/form-data"
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