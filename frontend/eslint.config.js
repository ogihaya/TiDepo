import js from '@eslint/js';
import tseslint from 'typescript-eslint';
import reactHooks from 'eslint-plugin-react-hooks';
import reactRefresh from 'eslint-plugin-react-refresh';
import boundaries from 'eslint-plugin-boundaries';
import prettier from 'eslint-config-prettier';

// FSD のレイヤー依存方向（上→下の一方通行）を eslint-plugin-boundaries で強制する。
// app → pages → widgets → features → entities → shared
export default tseslint.config(
  { ignores: ['dist', 'node_modules', 'coverage'] },
  js.configs.recommended,
  tseslint.configs.recommended,
  {
    files: ['**/*.{ts,tsx}'],
    plugins: {
      'react-hooks': reactHooks,
      'react-refresh': reactRefresh,
      boundaries,
    },
    settings: {
      // boundaries が `@/*` エイリアスの import 先レイヤーを解決できるよう、TS リゾルバを使う。
      // これが無いとエイリアス import の依存方向チェックが効かない。
      'import/resolver': {
        typescript: { project: './tsconfig.app.json' },
      },
      'boundaries/include': ['src/**/*'],
      'boundaries/elements': [
        { type: 'app', pattern: 'src/app', mode: 'folder' },
        { type: 'pages', pattern: 'src/pages', mode: 'folder' },
        { type: 'widgets', pattern: 'src/widgets', mode: 'folder' },
        { type: 'features', pattern: 'src/features', mode: 'folder' },
        { type: 'entities', pattern: 'src/entities', mode: 'folder' },
        { type: 'shared', pattern: 'src/shared', mode: 'folder' },
      ],
    },
    rules: {
      'react-hooks/rules-of-hooks': 'error',
      'react-hooks/exhaustive-deps': 'warn',
      'react-refresh/only-export-components': ['warn', { allowConstantExport: true }],
      'boundaries/dependencies': [
        'error',
        {
          default: 'disallow',
          rules: [
            {
              from: { type: 'app' },
              allow: { to: { type: ['pages', 'widgets', 'features', 'entities', 'shared'] } },
            },
            {
              from: { type: 'pages' },
              allow: { to: { type: ['widgets', 'features', 'entities', 'shared'] } },
            },
            {
              from: { type: 'widgets' },
              allow: { to: { type: ['features', 'entities', 'shared'] } },
            },
            { from: { type: 'features' }, allow: { to: { type: ['entities', 'shared'] } } },
            { from: { type: 'entities' }, allow: { to: { type: ['shared'] } } },
            { from: { type: 'shared' }, allow: { to: { type: ['shared'] } } },
          ],
        },
      ],
    },
  },
  prettier,
);
