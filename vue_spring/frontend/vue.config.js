module.exports = {
    runtimeCompiler: true,
    devServer: {
        proxy: {
            '/api': {target: 'http://localhost:8399', ws: true, changeOrigin: true},
        }
    }
}