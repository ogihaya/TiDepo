/// <reference types="vitest/config" />
import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import { fileURLToPath, URL } from 'node:url';

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
  server: {
    // コンテナ外（ホスト）からアクセスできるよう全インターフェースで待受
    host: true,
    // Docker の bind mount ではファイル変更イベントが届かないことがあるため、
    // コンテナ内でのみ polling を有効化する（compose で VITE_USE_POLLING=true を設定）
    watch: {
      usePolling: process.env.VITE_USE_POLLING === 'true',
    },
    // 開発時、/api/* を backend コンテナへ転送（CORS 回避。本番は別途リバースプロキシ）
    proxy: {
      '/api': {
        target: 'http://backend:8080',
        changeOrigin: true,
      },
    },
  },
  test: {
    environment: 'jsdom',
    globals: false,
    setupFiles: ['./vitest.setup.ts'],
  },
});
