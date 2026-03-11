import { useEffect, useState } from 'react'
import { getCurrentWindow } from '@tauri-apps/api/window'

import { WindowControls } from './window-controls'

export function WindowTitleBar() {
  const [isFullscreen, setFullscreen] = useState(false)

  useEffect(() => {
    ;(async function () {
      const currentWindow = getCurrentWindow()
      currentWindow.onResized(async function () {
        const isFullscreen = await currentWindow.isFullscreen()
        setFullscreen(isFullscreen)
      })
    })()
  }, [])

  async function handleDragRegionMouseDown(
    event: React.MouseEvent<HTMLDivElement>
  ) {
    const currentWindow = getCurrentWindow()
    const isFullscreen = await currentWindow.isFullscreen()
    if (event.button !== 0 || isFullscreen) return

    if (event.detail === 2) {
      await currentWindow.toggleMaximize()
    }

    await currentWindow.startDragging()
  }

  return (
    <header className="flex h-11 items-center">
      <div className="flex items-center gap-2 px-4">
        <div className="bg-meteorite-500 size-4 rounded-full" />
      </div>

      <div
        className="h-full flex-1"
        aria-hidden
        onMouseDown={(event) => void handleDragRegionMouseDown(event)}></div>

      {!isFullscreen && <WindowControls />}
    </header>
  )
}
