# 📸 Camtu

<p align="center">
  Turn your phone into a secure local webcam for your computer with low-latency real-time streaming.
</p>

<p align="center">
  <img src="https://img.shields.io/github/actions/workflow/status/luas10c/camtu/ci.yml?branch=develop&label=CI" alt="CI Status" />
  <img src="https://img.shields.io/badge/ESLint-checked-4B32C3?logo=eslint" alt="ESLint" />
  <img src="https://img.shields.io/badge/Prettier-formatted-F7B93E?logo=prettier&logoColor=000" alt="Prettier" />
  <img src="https://img.shields.io/badge/tests-Jest-6E9F18" alt="Jest" />
  <img src="https://img.shields.io/badge/license-MIT-green" alt="License" />
</p>

## Overview

**Camtu** turns your phone into a **secure local webcam** for your computer.

This monorepo contains:

- **Web** app built with React + Vite
- **Mobile** app built with React Native
- **Desktop** app built with Tauri + Rust

## 🚀 Tecnologias Utilizadas

- [React](https://react.dev/)
- [React Native](https://reactnative.dev/) (Mobile)
- [Tauri](https://v2.tauri.app/start/) (Desktop)
- [TypeScript](https://www.typescriptlang.org/)
- [Jest](https://jestjs.io/) (Testes Unitários)
- [ESLint](https://eslint.org/) & [Prettier](https://prettier.io/) (Padronização)

---

## 🛠️ Como Iniciar

Antes de começar, você precisará ter instalado em sua máquina o [Node.js](https://nodejs.org/) e um gerenciador de pacotes (npm, yarn ou pnpm).

### 1. Clone o repositório
```bash
git clone [https://github.com/luas10c/camtu.git](https://github.com/luas10c/camtu.git)
cd camtu
```

### 2. Instale as dependências
```bash
npm install
# ou
yarn install
```

## 💻 Executando as Versões

### 🌐 Web
Para rodar a versão web no navegador:
```
npm run dev:web
```

### 🖥️ Desktop (Windows/macOS/Linux)
A versão desktop utiliza Tauri para empacotar a aplicação:
```bash
npm run dev:desktop
```

### 📦 Build (Produção)
Para gerar as versões finais de distribuição:
- Web: npm run build:web
- Desktop: npm run build:desktop
- Mobile(android): npm run build-android:mobile
- Mobile(ios): npm run build-ios:mobile

## 📄 Licença
Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.
