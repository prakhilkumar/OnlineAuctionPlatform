import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Login = () => {
  const navigate = useNavigate();
  const [role, setRole] = useState('User');
  const [password, setPassword] = useState('');
  const [email, setEmail] = useState('');
  const [errorMessage, setErrorMessage] = useState(''); // New state to track error message
  

  const loginHandler = async (e) => {
    e.preventDefault();

    console.log("this is our data "+ email +"   "+ password )
    
    const data = {
        "userId": email,
        "password":password
    }

    try{
        const response = await axios.post("http://localhost:8082/loginRequest", data);

        console.log("this is the response " + response.data);
        if(!response.data) {
            alert("Invalid User Id or Password");
        }
        else {
            alert("Login Successfull");
            console.log(response.data.user_id);
            const userIdFromResponse = response.data.user_id;
            sessionStorage.setItem('userId', userIdFromResponse);
            sessionStorage.setItem("accessToken",response.data.accessToken);
            if(role==="Admin")
              navigate("/AdminDashBoard")
            if(role==="User")
              navigate("/UserDashBoard")

        }
        
    } catch(error) {
        console.error(error);
    }
  };
  return (
    <div className="flex items-center justify-center min-h-screen bg-cover bg-center bg-gray-50" style={{ backgroundImage: `url('https://s3-us-east-2.amazonaws.com/website-tips-tutorials/WP+Media+Folder+-+Website+Tips+and+Tutorials/wp-content/uploads/2019/07/auction-website-how-to-build-create-wordpress-woocommerce-bluehost-696x522.png')` }}>
      <form onSubmit={loginHandler} className="bg-white bg-opacity-90 p-8 rounded-lg shadow-lg w-full max-w-sm backdrop-blur-sm">
        <h2 className="text-2xl font-bold text-primary mb-6 text-center">Login to Your Account</h2>
        
        {errorMessage && (
          <div className="bg-red-100 text-red-800 p-3 mb-6 rounded-lg text-center">
            {errorMessage}
          </div>
        )}

        <label htmlFor="email" className="block text-gray-700 mb-2">Email</label>
        <input
          type="email"
          name="email"
          id="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-primary focus:ring-1 focus:ring-primary mb-4"
          required
        />

        <label htmlFor="password" className="block text-gray-700 mb-2">Password</label>
        <input
          type="password"
          name="password"
          id="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-primary focus:ring-1 focus:ring-primary mb-4"
          required
        />

        <label htmlFor="UserType" className="block text-gray-700 mb-2">User Type</label>
        <select
          onChange={(e) => setRole(e.target.value)}
          value={role}
          name="UserType"
          id="UserType"
          className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-primary focus:ring-1 focus:ring-primary mb-6"
        >
          <option value="User">User</option>
          <option value="Admin">Admin</option>
        </select>

        <button
          type="submit"
          className="w-full bg-primary text-white px-4 py-2 rounded-md shadow-md hover:bg-blue-600 focus:bg-blue-700 transition duration-300 ease-in-out cursor-pointer font-semibold"
        >
          Submit
        </button>
        <br></br>
        <br></br>
        <button
          type="submit"
          className="w-full bg-primary text-white px-4 py-2 rounded-md shadow-md hover:bg-blue-600 focus:bg-blue-700 transition duration-300 ease-in-out cursor-pointer font-semibold"
        onClick={()=>{navigate("/SignUp")}}>
          <p>Don't have account , SignUp</p>
        </button>
      </form>
    </div>
  );
};

export default Login;
