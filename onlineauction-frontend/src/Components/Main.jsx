import React from 'react';
import { useNavigate } from 'react-router-dom';

const Main = () => {
  const navigate = useNavigate();

  function onClickHandler(target) {
    if (target === 'Login') navigate('/Login');
    else navigate('/SignUp');
  }

  return (
    <div
      className="flex items-center justify-center min-h-screen bg-cover bg-center bg-no-repeat"
      style={{
        backgroundImage: `url('https://s3-us-east-2.amazonaws.com/website-tips-tutorials/WP+Media+Folder+-+Website+Tips+and+Tutorials/wp-content/uploads/2019/07/auction-website-how-to-build-create-wordpress-woocommerce-bluehost-696x522.png')`,
      }}
    >
      <div className="bg-white bg-opacity-90 p-8 rounded-lg shadow-lg text-center max-w-md">
        <h1 className="text-3xl font-bold text-primary mb-6">Welcome to the Auction Platform</h1>
        <p className="text-darkGray mb-8">
          Find and bid on unique items, or create your own auction!
        </p>
        <div className="space-y-4">
          <button
            onClick={() => onClickHandler('Login')}
            className="bg-primary text-white w-full px-6 py-3 rounded-md font-semibold shadow-md hover:bg-opacity-90 transition duration-300 ease-in-out"
          >
            Login
          </button>
          <button
            onClick={() => onClickHandler('SignUp')}
            className="bg-accent text-white w-full px-6 py-3 rounded-md font-semibold shadow-md hover:bg-opacity-90 transition duration-300 ease-in-out"
          >
            Sign Up
          </button>
        </div>
      </div>
    </div>
  );
};

export default Main;
