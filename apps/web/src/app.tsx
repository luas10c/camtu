import { useHotkeys } from '@tanstack/react-hotkeys'
import { exit, relaunch } from '@tauri-apps/plugin-process'
import { getCurrentWindow } from '@tauri-apps/api/window'
import { WindowTitleBar } from './components/window-title-bar'

export function App() {
  useHotkeys([
    {
      hotkey: 'Control+R',
      async callback() {
        await relaunch()
      }
    },
    {
      hotkey: 'Control+Q',
      async callback() {
        await exit()
      }
    },
    {
      hotkey: 'F11',
      async callback() {
        const currentWindow = getCurrentWindow()
        const isFullscreen = await currentWindow.isFullscreen()
        await currentWindow.setFullscreen(!isFullscreen)
      }
    }
  ])

  return (
    <>
      <WindowTitleBar />
      <section className="text-bunker-100 relative flex flex-1 flex-col p-4">
        <h2 className="text-xl font-bold text-white">Hello, World</h2>
      </section>
    </>
  )
}
