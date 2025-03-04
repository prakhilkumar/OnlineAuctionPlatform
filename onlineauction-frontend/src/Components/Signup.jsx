import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Signup = () => {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [number, setNumber] = useState('');
  const navigate = useNavigate();
  

  const submitHandler = async (e) => {
    const data={
      "name":name,
      "email":email,
      "password":password,
      "mobilenum":number
    }
    e.preventDefault();
        

        try{
            const response = await axios.post('http://localhost:8082/addUser', data);
            console.log(response.data);
            if(!response.data) {
              alert("Invalid User Id or Password");
          }
          else {
              alert("SignUp Successfull");
              navigate("/login");
  
          }

       } catch(error){
        console.log(error);

       }
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-cover bg-center" style={{ backgroundImage: `url('https://s3-us-east-2.amazonaws.com/website-tips-tutorials/WP+Media+Folder+-+Website+Tips+and+Tutorials/wp-content/uploads/2019/07/auction-website-how-to-build-create-wordpress-woocommerce-bluehost-696x522.png')` }}>
      <form onSubmit={submitHandler} className="bg-white bg-opacity-90 p-8 rounded-lg shadow-lg w-full max-w-md backdrop-blur-sm">
        <h2 className="text-2xl font-bold text-primary mb-6 text-center">Sign Up</h2>

        <div className="mb-4">
          <label htmlFor="name" className="block text-sm font-bold mb-2">Name:</label>
          <input
            type="text"
            id="name"
            name="name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
            className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-primary focus:ring-1 focus:ring-primary"
          />
        </div>

        <div className="mb-4">
          <label htmlFor="email" className="block text-sm font-bold mb-2">Email:</label>
          <input
            type="email"
            id="email"
            name="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
            className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-primary focus:ring-1 focus:ring-primary"
          />
        </div>

        <div className="mb-4">
          <label htmlFor="password" className="block text-sm font-bold mb-2">Password:</label>
          <input
            type="password"
            id="password"
            name="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-primary focus:ring-1 focus:ring-primary"
          />
        </div>

        <div className="mb-4">
          <label htmlFor="number" className="block text-sm font-bold mb-2">Phone Number:</label>
          <input
            type="text"
            id="number"
            name="number"
            value={number}
            onChange={(e) => setNumber(e.target.value)}
            required
            className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-primary focus:ring-1 focus:ring-primary"
          />
        </div>

        <button 
          type="submit" 
          className="bg-primary text-white px-4 py-2 rounded-md shadow-md hover:bg-blue-600 focus:bg-blue-700 transition duration-300 cursor-pointer w-full font-semibold"
        >
          Submit
        </button>
      </form>
    </div>
  );
};

export default Signup;
