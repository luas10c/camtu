import { defineConfig, globalIgnores } from 'eslint/config'

import globals from 'globals'

import js from '@eslint/js'

import ts from 'typescript-eslint'

import prettier from 'eslint-plugin-prettier'
import react from 'eslint-plugin-react'
import a11y from 'eslint-plugin-jsx-a11y'
import refresh from 'eslint-plugin-react-refresh'

import tl from 'eslint-plugin-testing-library'

export default defineConfig([
  globalIgnores(['node_modules', 'dist', 'coverage']),
  js.configs.recommended,
  ts.configs.recommended,
  {
    plugins: {
      prettier,
      react,
      'jsx-a11y': a11y,
      'react-refresh': refresh
    },
    languageOptions: {
      globals: {
        ...globals.node,
        ...globals.browser,
        ...globals.es2022,
        React: true,
        JSX: true
      }
    },
    settings: {
      react: {
        version: 'detect'
      }
    },
    rules: {
      ...prettier.configs.recommended.rules,
      ...react.configs.recommended.rules,
      ...react.configs['jsx-runtime'].rules,
      ...refresh.configs.recommended.rules,
      ...a11y.configs.recommended.rules
    }
  },
  {
    files: ['**/*.{test|spec}.ts'],
    plugins: {
      'testing-library': tl
    },
    rules: {
      ...tl.configs['flat/react'].rules
    }
  }
])
