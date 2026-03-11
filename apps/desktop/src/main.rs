// Prevents additional console window on Windows in release, DO NOT REMOVE!!
#![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]


/*
use v4l::prelude::*;
use v4l::io::mmap::Stream;
use v4l::buffer::Type;

use std::io::Write;

use tokio::net::{TcpListener, TcpStream};

use v4l::video::Capture;
use v4l::Format;
use v4l::FourCC;
use v4l::Device;

fn write_frame_to_virtual_camera(data: &[u8]) -> std::io::Result<()> {
    let mut dev = Device::with_path("/dev/video10")?;

    let fmt = Format::new(1920, 1080, FourCC::new(b"YUYV"));
    dev.set_format(&fmt)?;

    dev.write(data)?;
    Ok(())
}

async fn handle_connection(stream: TcpStream) {
    // handle the connection asynchronously here
}

async fn aaaa() -> std::io::Result<()> {
  let listener = TcpListener::bind("127.0.0.1:9001").await?;
  while let Ok((stream, _)) = listener.accept().await {
    tokio::spawn(handle_connection(stream));
  }

  Ok(())
}

fn create_device() -> Result<Stream<'static>, Box<dyn std::error::Error>> {
  let mut device = v4l::Device::new(10)?;

  let name = device.query_caps()?.driver;
  println!("Criando device com nome: {}", name);

  let stream: MmapStream<'_> = Stream::new(&mut device,  Type::VideoCapture)?;

  Ok(stream)
}
*/

#[tokio::main]
async fn main() {
    tauri::Builder::default()
        .invoke_handler(tauri::generate_handler![])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
