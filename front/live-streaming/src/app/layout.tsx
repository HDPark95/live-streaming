import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "Live Streaming App",
  description: "Clone Youtube",
};

import React, { ReactNode } from 'react';
import Head from 'next/head';
import AuthSession from "@/app/_component/AuthSession";
import {ThemeProvider} from "@/providers/themeProvider";
import Sidebar from "@/components/Sidebar";
import {MSWComponent} from "@/app/_component/MSWComponent";

interface RootLayoutProps {
  children: ReactNode;
}

export default function RootLayout({ children }: RootLayoutProps) {
  return (
      <html lang="en">
      <body className={inter.className}>
      <MSWComponent />
      <ThemeProvider
          attribute="class"
          defaultTheme="dark"
          enableSystem
          disableTransitionOnChange
      >
          <AuthSession>
          {children}
          </AuthSession>
      </ThemeProvider>
      </body>
      </html>
  );
}