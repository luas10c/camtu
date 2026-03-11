// Prevents additional console window on Windows in release, DO NOT REMOVE!!
#![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]

use tauri_plugin_log::{Target, TargetKind};

#[tokio::main]
async fn main() {
    tauri::Builder::default()
        .plugin(tauri_plugin_opener::init())
        .plugin(tauri_plugin_process::init())
        .plugin(tauri_plugin_log::Builder::new()
          .targets([
              Target::new(TargetKind::Stdout),
              Target::new(TargetKind::LogDir { file_name: Some("keira".into()) }),
          ])
          .format(|out, message, record| {
              out.finish(format_args!(
                  "[{}] {}",
                  record.level(),
                  message
              ))
          })
          .level(log::LevelFilter::Info)
          .build()
        )
        .plugin(tauri_plugin_store::Builder::new().build())
        .setup(|app| {
          #[cfg(desktop)]
          let _ = app.handle().plugin(tauri_plugin_updater::Builder::new().build());

          #[cfg(desktop)]
          let _ = app.handle().plugin(tauri_plugin_positioner::init());

          Ok(())
        })
        .invoke_handler(tauri::generate_handler![])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
