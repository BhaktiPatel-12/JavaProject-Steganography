# JavaProject-Steganography
Java-based steganography tool that hides and extracts secret messages in images using pixel manipulation


# StagnoGraphy - Java Image Steganography Tool

**StagnoGraphy** is a lightweight Java application that implements basic **steganography** techniques to hide and retrieve secret messages within images by manipulating pixel data.

This project uses **Pixlr-compatible image formats and pixel-level operations** to seamlessly encode text into images without visibly altering them.


## Features

- Hide secret messages inside images (PNG, JPEG)
- Extract hidden messages from encoded images
- Uses lossless techniques for minimal image quality loss
- Simple CLI or GUI (optional) for encoding/decoding
- Educational example of steganography using Java



##  How It Works

The tool encodes each character of the message into the **least significant bits (LSB)** of the image's pixel data. Since human eyes can't detect small pixel changes, the message remains invisible but recoverable.



## 📦 Project Structure

