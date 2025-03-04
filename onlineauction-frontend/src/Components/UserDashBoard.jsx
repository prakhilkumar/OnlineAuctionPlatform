import React, { useState, useEffect } from 'react';
import axios from 'axios';
import moment from 'moment';
import { useNavigate } from 'react-router-dom';

const UserDashboard = () => {
  const [bids, setBids] = useState([]);
  const [bidAmount, setBidAmount] = useState('');
  const [showBidInput, setShowBidInput] = useState(false);
  const [selectedBidId, setSelectedBidId] = useState(null);
  const [activeTab, setActiveTab] = useState('Available');
  const [hasMembership, setHasMembership] = useState(false);
  const [userName, setUserName] = useState('');
  const [state,setState]=useState("")
  const navigate = useNavigate();
  const userId = sessionStorage.getItem('userId');
  
  useEffect(() => {
    const interval = setInterval(() => {
      setBids((prevBids) =>
        prevBids.map((bid) => ({
          ...bid,
          remainingTime: calculateRemainingTime(bid.endDate),
        }))
      );
    }, 100); 

    return () => clearInterval(interval);
  }, []);

  useEffect(() => {
    fetchBids();
    checkMembership();
    fetchUserName();
  }, [activeTab]);

   // Fetch User Name
   const fetchUserName = async () => {
    const accessToken = sessionStorage.getItem('accessToken');
    try {
      const response = await axios.get(`http://localhost:8082/user/details`,{
        params:{
          'authToken':accessToken
        }
      });
      const username = response.data; 
      setUserName(username);
    } catch (error) {
      console.error("Error fetching user details:", error);
    }
  };


  // Fetch Bids
  const fetchBids = async () => {
    try {
      const accessToken = sessionStorage.getItem('accessToken'); // Get the access token from sessionStorage
      console.log("################# accessToken #################")
      console.log(accessToken)
      const endpoint =
        activeTab === 'Available'
          ? `http://localhost:8082/auction/bids?tabType=AVAILBID`
          : activeTab === 'Created'
          ? `http://localhost:8082/auction/bids?tabType=CREATEDBID`
          : `http://localhost:8082/auction/user/bids`;
  
      const response = await axios.get(endpoint, {
        params: {
          'authToken': accessToken
        }
      });
  
      setBids(response.data); // Set the bids data
    } catch (error) {
      console.error(`Error fetching ${activeTab} bids:`, error);
    }
  };
  

  // Check Membership
  const checkMembership = async () => {
    const accessToken = sessionStorage.getItem('accessToken');
    try {
      const response = await axios.get(
        `http://localhost:8082/auction/user/memberships`,{
          params: { 
            'authToken': accessToken
          }
      });
      const memberships = response.data;
      setHasMembership(memberships && memberships.length > 0);
    } catch (error) {
      console.error('Error checking membership:', error);
    }
  };

  // Place Bid
  const placeBid = async (bidId) => {
    if (!bidAmount || bidAmount <= 0) {
      alert('Please enter a valid bid amount.');
      return;
    }
    const accessToken = sessionStorage.getItem('accessToken');
    try {
      const response = await axios.post(`http://localhost:8082/auction/Bid/place-bid/${bidId}`, {
        bidAmount
      },{
        params: { 
          'authToken': accessToken
        }
      });
      if (response.status === 200) {
        alert('Bid placed successfully!');
        setShowBidInput(false);
        setBidAmount('');
        fetchBids();
      } else {
        alert('Failed to place bid. Try again.');
      }
    } catch (error) {
      console.error('Error placing bid:', error);
      alert(error.response.data.msg);;
    }
  };

  // Helper to calculate remaining time
  const calculateRemainingTime = (endDate) => {
    const now = moment();
    const end = moment(endDate);
    const duration = moment.duration(end.diff(now));
    const days = Math.floor(duration.asDays());
    const hours = duration.hours();
    const minutes = duration.minutes();
    const seconds = duration.seconds();

    if (end.isBefore(now)) {
      return 'Expired';
    }

    return days > 0
      ? `${days} days ${hours} hours ${minutes} minutes ${seconds} seconds remaining`
      : hours > 0
      ? `${hours} hours ${minutes} minutes ${seconds} seconds remaining`
      : minutes > 0
      ?`${minutes} minutes ${seconds} seconds remaining`
      : `${seconds} seconds remaining`;
  };
  const handlePlaceBidClick = (bidId) => {
    setSelectedBidId(bidId); // Set the selected bid id
    setShowBidInput(true); // Show the input field after clicking the button
  };

  // Handle Sell Bid Item API call
  const handleSellBidItem = async (bidItemId) => {
    const accessToken = sessionStorage.getItem('accessToken');
    try {
      const response = await axios.put(
        `http://localhost:8082/auction/bid/item/sell?bidItemId=${bidItemId}`,null,{
          params : {
            'authToken' : accessToken
          }
        }
      );
      if (response.status === 200) {
        alert('Bid item sold successfully!');
        fetchBids(); // Refresh bids after selling
      }
    } catch (error) {
      console.error('Error selling bid item:', error);
      alert(error.response.data.msg);
      fetchBids();
    }
  };

  return (
    <div className="bg-black text-gray-200 min-h-screen p-6">
      {/* Header Section */}
      <div className="fixed top-0 left-0 right-0 z-10 bg-gray-900 shadow-lg py-4 px-6">
        <div className="flex items-center justify-between max-w-6xl mx-auto">
          <h2 className="text-3xl font-bold text-blue-400">
            Online Auction Platform
          </h2>
          <div className="flex items-center space-x-6">
            <span className="text-lg text-gray-300">Welcome, {userName}!</span> {/* Display user name */}
            <button
              onClick={() => navigate('/create-bid')}
              className="px-6 py-2 bg-blue-500 hover:bg-blue-600 text-white font-semibold rounded-lg shadow-md transition"
            >
              New Bid
            </button>
            {hasMembership ? (
              <span className="text-green-400 font-semibold">
                You are a Premium Member!
              </span>
            ) : (
              <button
                onClick={() => navigate('/premium-membership')}
                className="px-6 py-2 bg-blue-500 hover:bg-blue-600 text-white font-semibold rounded-lg shadow-md transition"
              >
                Premium
              </button>
            )}
            <button
              onClick={() => navigate('/user-wallet')}
              className="px-6 py-2 bg-purple-500 hover:bg-purple-600 text-white font-semibold rounded-lg shadow-md transition"
            >
              Wallet
            </button>
            <button
              onClick={() => {
                sessionStorage.removeItem('isLoggedIn');
                navigate('/Login');
              }}
              className="px-6 py-2 bg-red-500 hover:bg-red-600 text-white font-semibold rounded-lg shadow-md transition"
            >
              Log Out
            </button>
          </div>
        </div>
      </div>

      {/* Tabs */}
      <div className="max-w-6xl mx-auto mt-20 flex space-x-6 justify-center">
        <button
          onClick={() => setActiveTab('Available')}
          className={`py-2 px-6 rounded-lg transition ${
            activeTab === 'Available'
              ? 'bg-blue-600 text-white'
              : 'bg-gray-700 text-gray-300'
          }`}
        >
          Available Bids
        </button>
        {<button
          onClick={() => setActiveTab('Placed')}
          className={`py-2 px-6 rounded-lg transition ${
            activeTab === 'Placed'
              ? 'bg-blue-600 text-white'
              : 'bg-gray-700 text-gray-300'
          }`}
        >
          Placed Bids
        </button>}
        <button
          onClick={() => setActiveTab('Created')}
          className={`py-2 px-6 rounded-lg transition ${
            activeTab === 'Created'
              ? 'bg-blue-600 text-white'
              : 'bg-gray-700 text-gray-300'
          }`}
        >
          Created Bids
        </button>
      </div>

      {/* Bids List */}
      <div className="max-w-6xl mx-auto mt-10">
        <h3 className="text-2xl font-semibold mb-6 text-blue-400">
          {activeTab} Bids
        </h3>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
          {bids.map((bid) => (
            <div
              key={bid.id}
              className="bg-gray-900 p-6 rounded-lg shadow-lg hover:shadow-xl transition duration-300 ease-in-out"
            >
              {  bid.imageData && (
                <div class="relative w-full h-64 rounded-lg overflow-hidden">
                   <img             src={`data:image/jpeg;base64,${bid.imageData}`}
                                    alt="img not found"
                                    className="" />
                </div>
               )}
              <h4 className="text-xl font-bold text-blue-400">{bid.title}</h4>
              <p className="text-sm text-gray-400 mt-2">{bid.description}</p>
              <p className="mt-4 font-semibold text-gray-300">Price: ${bid.price}</p>
              <p className="mt-1 text-gray-400">
                End Date: {moment(bid.endDate).format('YYYY-MM-DD HH:mm')}
              </p>
              <p className="mt-1 text-gray-400">Status: {bid.status}</p>
              {  activeTab==='Placed' && (
                <p className="mt-1 text-gray-400">Your Maximum Bid: {bid.bidAmount}</p>
               )}
              <p className="mt-1 text-gray-400">Current Maximum Bid: {bid.maxBidAmount}</p>
              <p className="mt-1 text-gray-400">
                Remaining Time: {calculateRemainingTime(bid.endDate)}
              </p>
              {/* Show Place Bid Button if Available */}
              {state!==bid.id && activeTab === 'Available' && (
   (
    <button
      onClick={() => {
        setState(bid.id)
        setSelectedBidId(bid.id);
        setShowBidInput(true);
      }}
      className={`mt-4 px-6 py-2 ${
        moment(bid.endDate).isAfter(moment())
          ? 'bg-green-500 hover:bg-green-600'
          : 'bg-gray-400 cursor-not-allowed'
      } text-white font-semibold rounded-lg shadow-md`}
      disabled={!moment(bid.endDate).isAfter(moment())}
    >
      Place Bid
    </button>
  )
)}




              {/* Bid Input Field */}
              {activeTab === 'Available' && showBidInput && selectedBidId === bid.id && (
                <div className="mt-4">
                  <input
                    type="number"
                    className="px-4 py-2 w-full bg-gray-700 text-white rounded-md"
                    value={bidAmount}
                    onChange={(e) => setBidAmount(e.target.value)}
                    placeholder="Enter your bid"
                  />
                  <button
                    onClick={() =>{ 
                      setState("")
                      placeBid(bid.id)}}
                    className="mt-2 px-6 py-2 bg-blue-500 hover:bg-blue-600 text-white font-semibold rounded-lg shadow-md"
                  >
                    Submit Bid
                  </button>
                </div>
              )}

              {/* Show Sell Button if Bid is Expired */}
              {activeTab === 'Created' && moment(bid.endDate).isBefore(moment()) && (
  <button
    onClick={() => handleSellBidItem(bid.id)}
    className={`mt-4 px-6 py-2 text-white font-semibold rounded-lg shadow-md ${
      bid.status !== 'AVAILABLE'
        ? 'bg-gray-400 cursor-not-allowed'
        : 'bg-red-500 hover:bg-red-600'
    }`}
    disabled={bid.status !== 'AVAILABLE'}
  >
    Sell Item
  </button>
)}

            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default UserDashboard;
