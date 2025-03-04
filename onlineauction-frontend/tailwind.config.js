// tailwind.config.js
module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: '#1E40AF', 
        accent: '#F59E0B',        
        background: '#F3F4F6',    
        darkGray: '#4B5563',      
      },
    },
  },
  plugins: [],
};
