import { defineConfig } from 'vite'

// https://vite.dev/config/
export default defineConfig({
  server: {
    port: 3000,
    proxy: {
      "/api": {
        target: "http://localhost:8080",
        changeOrigin: true,
        configure: (proxy) => {
          proxy.on("error", (err, req, res) => {
            console.log("error connection upstream")
            res.writeHead(502)
            res.end()
          })
          proxy.on("proxyRes", (proxyRes, _, res) => {
            const upstreamSocket = proxyRes.socket
            console.log("upstream connected")
            if(upstreamSocket) {
              upstreamSocket.once('close', () => {
                console.log("upstream closed")
                if(!res.writableFinished) {
                  console.log("destroying downstream")
                  res.destroy()
                }
              })
            }
          })
        },
      }
    }
  }
})