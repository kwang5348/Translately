module.exports = {
    runtimeCompiler: true,
    devServer: {
        proxy: {
            '/api': {target: 'http://i3a511.p.ssafy.io:8399/', ws: true, changeOrigin: true},
        }
    }
}