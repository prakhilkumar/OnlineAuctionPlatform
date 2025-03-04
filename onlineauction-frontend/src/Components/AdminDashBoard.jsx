import React, { useState, useEffect } from 'react';
import axios from 'axios';

const AdminDashboard = () => {
  const [bids, setBids] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:3000/Bid')
      .then((response) => {
        setBids(response.data);
      })
      .catch((error) => console.error(error));
  }, []);

  const handleDeleteBid = (id) => {
    axios.delete(`http://localhost:3000/Bid/${id}`)
      .then(() => {
        setBids(bids.filter((bid) => bid._id !== id));
      })
      .catch((error) => console.error(error));
  };

  const handleMarkAsCompleted = (id) => {
    axios.put(`http://localhost:3000/Bid/${id}`, { status: 'completed' })
      .then((response) => {
        setBids(bids.map((bid) => (bid._id === id ? response.data : bid)));
      })
      .catch((error) => console.error(error));
  };

  return (
    <div className="p-8 bg-gray-100 min-h-screen">
      <h2 className="text-3xl font-bold mb-6">Admin Dashboard</h2>
      <ul className="space-y-4">
        {bids.map((bid) => (
          <li key={bid._id} className="bg-white p-4 rounded-lg shadow-md">
            <h3 className="text-xl font-semibold">{bid.title}</h3>
            <p className="font-medium">Price: ${bid.price}</p>
            <p>Status: <span className={`font-semibold ${bid.status === 'completed' ? 'text-green-500' : 'text-red-500'}`}>{bid.status}</span></p>
            <div className="mt-4">
              <button
                onClick={() => handleMarkAsCompleted(bid._id)}
                className="bg-blue-500 text-white py-1 px-3 rounded hover:bg-blue-600 transition duration-300 mr-2"
              >
                Mark as Completed
              </button>
              <button
                onClick={() => handleDeleteBid(bid._id)}
                className="bg-red-500 text-white py-1 px-3 rounded hover:bg-red-600 transition duration-300"
              >
                Delete
              </button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default AdminDashboard;
