import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const CreateBidForm = () => {
  const [newBid, setNewBid] = useState({
    title: '',
    description: '',
    price: '',
    endDate: '',
  });
  const [image, setImage] = useState(null);
  const navigate = useNavigate();
  const userId = sessionStorage.getItem('userId');

  const handleNewBidChange = (e) => {
    const { name, value } = e.target;
    setNewBid((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };
  const handleImageChange = (e) => {
    setImage(e.target.files[0]);
    
  };


  const handleCreateBid = () => {
    const accessToken = sessionStorage.getItem('accessToken');
    const { title, description, price, endDate } = newBid;
    if (!title || !description || !price || !endDate) {
      alert('Please fill in all fields');
      return;
    }

    const currentDate = new Date();
    const selectedEndDate = new Date(endDate);

    if (
      selectedEndDate < currentDate ||
      (selectedEndDate.toDateString() === currentDate.toDateString() &&
        selectedEndDate.getTime() <= currentDate.getTime())
    ) {
      alert('End date and time must be in the future');
      return;
    }
    const formData = new FormData();
    formData.append("imageFile", image);
    formData.append(
      "bidDto",
      new Blob([JSON.stringify(newBid)], { type: "application/json" })
    );

    axios
      .post('http://localhost:8082/auction/Bid', formData,{
        params: {
          'authToken': accessToken
        },
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then(() => {
        setNewBid({ title: '', description: '', price: '', endDate: '' });
        alert('Bid created successfully!');
        navigate('/UserDashBoard');
      })
      .catch((error) => {
        console.error('Error creating bid:', error);
        if(error.response.data.code==="400")
          alert(error.response.data.msg);
        else
        alert('Failed to create bid');
      });
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-black">
      <div className="max-w-md w-full p-8 border border-blue-700 rounded-lg shadow-lg bg-gray-900 text-gray-200">
        <h2 className="text-3xl font-bold text-blue-400 mb-6 text-center">Create a New Bid</h2>
        <div className="space-y-4">
          <div>
            <label htmlFor="title" className="block mb-2 text-gray-300 font-semibold">
              Bid Title
            </label>
            <input
              id="title"
              type="text"
              name="title"
              value={newBid.title}
              onChange={handleNewBidChange}
              placeholder="Enter the bid title"
              className="w-full px-4 py-2 rounded-lg border bg-gray-800 text-gray-300 focus:outline-none focus:ring focus:ring-blue-500"
            />
          </div>
          <div>
            <label htmlFor="description" className="block mb-2 text-gray-300 font-semibold">
              Bid Description
            </label>
            <input
              id="description"
              type="text"
              name="description"
              value={newBid.description}
              onChange={handleNewBidChange}
              placeholder="Enter the bid description"
              className="w-full px-4 py-2 rounded-lg border bg-gray-800 text-gray-300 focus:outline-none focus:ring focus:ring-blue-500"
            />
          </div>
          <div className="w-full px-4 py-2 rounded-lg border bg-gray-800 text-gray-300 focus:outline-none focus:ring focus:ring-blue-500">
          <label className="form-label">
            <h6>Image</h6>
          </label>
          <input
            className="form-control"
            type="file"
            onChange={handleImageChange}
          />
        </div>
          <div>
            <label htmlFor="price" className="block mb-2 text-gray-300 font-semibold">
              Bid Price
            </label>
            <input
              id="price"
              type="number"
              name="price"
              value={newBid.price}
              onChange={handleNewBidChange}
              placeholder="Enter the bid price"
              className="w-full px-4 py-2 rounded-lg border bg-gray-800 text-gray-300 focus:outline-none focus:ring focus:ring-blue-500"
            />
          </div>
          <div>
            <label htmlFor="endDate" className="block mb-2 text-gray-300 font-semibold">
              End Date & Time
            </label>
            <input
              id="endDate"
              type="datetime-local"
              name="endDate"
              value={newBid.endDate}
              onChange={handleNewBidChange}
              className="w-full px-4 py-2 rounded-lg border bg-gray-800 text-gray-300 focus:outline-none focus:ring focus:ring-blue-500"
            />
          </div>
        </div>
        <button
          onClick={handleCreateBid}
          className="w-full mt-6 py-2 bg-blue-500 hover:bg-blue-600 text-white font-semibold rounded-lg shadow-md transition duration-300"
        >
          Create Bid
        </button>
      </div>
    </div>
  );
};

export default CreateBidForm;
