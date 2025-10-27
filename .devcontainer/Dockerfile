# Devcontainer Dockerfile to provide Chrome + required libs for running ChromeDriver locally
# Based on the official devcontainers base image for Ubuntu

FROM mcr.microsoft.com/devcontainers/base:ubuntu

ENV DEBIAN_FRONTEND=noninteractive

# Install basic tools and Chrome dependencies
RUN apt-get update \
    && apt-get install -y --no-install-recommends \
       ca-certificates \
       wget \
       gnupg \
       apt-transport-https \
       curl \
       unzip \
       lsb-release \
       fonts-liberation \
       libnss3 \
       libatk1.0-0 \
       libatk-bridge2.0-0 \
       libgtk-3-0 \
       libxss1 \
       libx11-xcb1 \
       libxcomposite1 \
       libxcursor1 \
       libxdamage1 \
       libxrandr2 \
       libxtst6 \
       libasound2 \
       libgbm1 \
       libdrm2 \
       libxshmfence1 \
    && rm -rf /var/lib/apt/lists/*

# Install Google Chrome (stable)
RUN wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | gpg --dearmor -o /usr/share/keyrings/google-linux-signing-keyring.gpg \
    && echo "deb [arch=amd64 signed-by=/usr/share/keyrings/google-linux-signing-keyring.gpg] http://dl.google.com/linux/chrome/deb/ stable main" \
       > /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update \
    && apt-get install -y --no-install-recommends google-chrome-stable \
    && rm -rf /var/lib/apt/lists/*

# Configure chrome binary path for tests
ENV CHROME_BIN=/usr/bin/google-chrome-stable

# Create a non-root user (vscode is default in devcontainers images, but ensure it exists)
ARG USERNAME=vscode
ARG USER_UID=1000
ARG USER_GID=$USER_UID

RUN if ! id -u $USERNAME >/dev/null 2>&1; then \
      groupadd --gid $USER_GID $USERNAME || true && \
      useradd -s /bin/bash --uid $USER_UID --gid $USER_GID -m $USERNAME || true; \
    fi

WORKDIR /workspace

# Default to non-interactive shell
ENV PATH="/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin"

# Keep image small by cleaning apt caches (done above) -- no extra steps
