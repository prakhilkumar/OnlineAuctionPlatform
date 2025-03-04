import React, { useEffect, useState } from 'react';
import axios from 'axios';

const UserWallet = () => {
  const [wallet, setWallet] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const userId = sessionStorage.getItem('userId');

  useEffect(() => {
    fetchWallet();
  }, []);

  const fetchWallet = async () => {
    const accessToken = sessionStorage.getItem('accessToken');
    try {
      const response = await axios.get(
        `http://localhost:8082/wallet/amount/`,
        {
          params: {
            authToken: accessToken,
          },
        }
      );
      setWallet(response.data);
      setLoading(false);
    } catch (err) {
      console.error('Error fetching wallet amount:', err);
      setError('Failed to fetch wallet amount.');
      setLoading(false);
    }
  };

  return (
    <div className="bg-black text-gray-200 min-h-screen p-6">
      <div className="max-w-4xl mx-auto mt-10">
        <h1 className="text-3xl font-bold text-blue-400 text-center mb-8">
          Your Wallet
        </h1>
        {loading ? (
          <p className="text-gray-400 text-center mt-6">Loading wallet amount...</p>
        ) : error ? (
          <p className="text-red-500 text-center mt-6">{error}</p>
        ) : (
          <div className="bg-gray-900 p-8 rounded-lg shadow-lg text-center">
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-6">
              <div>
                <h2 className="text-xl font-semibold text-gray-300 mb-2">
                  Total Wallet Amount
                </h2>
                <p className="text-3xl font-bold text-green-400">
                  ${wallet.totalAmount.toFixed(2)}
                </p>
              </div>
              <div>
                <h2 className="text-xl font-semibold text-gray-300 mb-2">
                  Total Lien Amount
                </h2>
                <p className="text-3xl font-bold text-red-400">
                  ${wallet.totalLienAmount.toFixed(2)}
                </p>
              </div>
              <div className="col-span-1 sm:col-span-2">
                <h2 className="text-xl font-semibold text-gray-300 mb-2">
                  Total Available Amount
                </h2>
                <p className="text-3xl font-bold text-blue-400">
                  ${(
                    wallet.totalAmount - wallet.totalLienAmount
                  ).toFixed(2)}
                </p>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default UserWallet;
