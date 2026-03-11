import { useState, useEffect } from 'react'
import { getCurrentWindow } from '@tauri-apps/api/window'

import { ReactComponent as ChromeMinimize } from '#/assets/chrome-minimize.svg'
import { ReactComponent as ChromeMaximize } from '#/assets/chrome-maximize.svg'
import { ReactComponent as ChromeRestore } from '#/assets/chrome-restore.svg'
import { ReactComponent as ChromeClose } from '#/assets/chrome-close.svg'

export function WindowControls() {
  const [isMaximized, setMaximized] = useState(false)

  async function handleAction(name: string) {
    const currentWindow = getCurrentWindow()

    const actions = {
      async minimize() {
        await currentWindow.minimize()
      },
      async maximize() {
        await currentWindow.maximize()
        setMaximized(true)
      },
      async restore() {
        await currentWindow.unmaximize()
        setMaximized(false)
      },
      async close() {
        await currentWindow.close()
      }
    } as const

    try {
      const action = actions[name as keyof typeof actions]
      if (action) await action()
    } catch {
      //
    }
  }

  useEffect(() => {
    ;(async function () {
      try {
        const currentWindow = getCurrentWindow()

        currentWindow.onResized(async function () {
          const isMaximized = await currentWindow.isMaximized()
          setMaximized(isMaximized)
        })

        const isMaximized = await currentWindow.isMaximized()
        setMaximized(isMaximized)
      } catch {
        //
      }
    })()
  }, [])

  return (
    <div className="flex items-center gap-4 px-4">
      <button
        type="button"
        className="focus-visible:outline-pelorous-400 flex size-6 cursor-pointer items-center justify-center rounded-sm outline-transparent focus:outline-2 focus:outline-offset-2"
        onClick={() => handleAction('minimize')}
        aria-label="Minimizar janela">
        <ChromeMinimize className="text-woodsmoke-500 size-3" />
      </button>
      {!isMaximized ? (
        <button
          type="button"
          className="focus-visible:outline-pelorous-400 flex size-6 cursor-pointer items-center justify-center rounded-sm outline-transparent focus:outline-2 focus:outline-offset-2"
          onClick={() => handleAction('maximize')}
          aria-label="Maximizar janela">
          <ChromeMaximize className="text-woodsmoke-500 size-3" />
        </button>
      ) : (
        <button
          type="button"
          className="focus-visible:outline-pelorous-400 flex size-6 cursor-pointer items-center justify-center rounded-sm outline-transparent focus:outline-2 focus:outline-offset-2"
          onClick={() => handleAction('restore')}
          aria-label="Restaurar janela">
          <ChromeRestore className="text-woodsmoke-500 size-3" />
        </button>
      )}

      <button
        type="button"
        className="focus-visible:outline-pelorous-400 flex size-6 cursor-pointer items-center justify-center rounded-sm outline-transparent focus:outline-2 focus:outline-offset-2"
        onClick={() => handleAction('close')}
        aria-label="Fechar janela">
        <ChromeClose className="text-woodsmoke-500 size-3" />
      </button>
    </div>
  )
}
