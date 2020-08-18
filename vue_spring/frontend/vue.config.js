module.exports = {
  "runtimeCompiler": true,
  "devServer": {
    "proxy": {
      "/api": {
        "target": "http://i3a511.p.ssafy.io:8301/",
        "ws": true,
        "changeOrigin": true
      }
    }
  },
  "transpileDependencies": [
    "vuetify"
  ]
}