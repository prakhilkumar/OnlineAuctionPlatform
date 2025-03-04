import React, { useEffect, useState } from "react";
import axios from "axios";
import Swal from "sweetalert2";
import { useNavigate } from "react-router-dom";

const PremiumMembership = () => {
  const [memberships, setMemberships] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);
  const [message, setMessage] = useState("");
  const navigate = useNavigate();
  const userId = sessionStorage.getItem("userId");

  useEffect(() => {
    // Fetch memberships list from the API
    const accessToken = sessionStorage.getItem("accessToken");
    const fetchMemberships = async () => {
      try {
        const response = await axios.get("http://localhost:8082/auction/memberships", {
          params: {
            authToken: accessToken,
          },
        });
        setMemberships(response.data);
        setIsLoading(false);
      } catch (err) {
        setError("Failed to fetch memberships.");
        setIsLoading(false);
      }
    };
    fetchMemberships();
  }, []);

  const handleSelectPlan = async (membershipId, membershipType) => {
    Swal.fire({
      title: "Are you sure?",
      text: `Do you want to upgrade to the ${membershipType} Membership plan?`,
      icon: "question",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Yes, upgrade",
      cancelButtonText: "No, cancel",
    }).then(async (result) => {
      if (result.isConfirmed) {
        try {
          const accessToken = sessionStorage.getItem("accessToken");
          await axios.post(
            `http://localhost:8082/auction/user/membership/upgrade?membershipId=${membershipId}`,
            null,
            {
              params: {
                authToken: accessToken,
              },
            }
          );
          Swal.fire(
            "Upgraded!",
            `You have successfully upgraded to the ${membershipType} Membership.`,
            "success"
          );
          setMessage(`Thank you for selecting the ${membershipType} plan!`);
        } catch (error) {
          console.error("Error upgrading membership:", error);
          Swal.fire(
            "Error",
            "Failed to upgrade your membership. Please try again.",
            "error"
          );
        }
      }
    });
  };

  if (isLoading) {
    return (
      <div className="flex items-center justify-center min-h-screen bg-gradient-to-br from-gray-900 via-gray-800 to-gray-900">
        <p className="text-gray-300 text-xl font-medium">Loading...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex items-center justify-center min-h-screen bg-gradient-to-br from-gray-900 via-gray-800 to-gray-900">
        <p className="text-red-500 text-xl font-medium">{error}</p>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-900 via-gray-800 to-gray-900 text-gray-300 py-12 px-6">
      <h1 className="text-4xl font-extrabold text-center text-blue-400 mb-12">
        Choose Your Membership
      </h1>
      {message ? (
        <div className="text-center">
          <p className="text-lg font-semibold text-green-400 mb-6">{message}</p>
          <button
            onClick={() => navigate("/UserDashboard")}
            className="px-6 py-3 bg-blue-600 hover:bg-blue-700 text-white font-semibold rounded-lg shadow-lg transition-transform transform hover:scale-105"
          >
            Back to Dashboard
          </button>
        </div>
      ) : (
        <div className="grid gap-10 grid-cols-1 sm:grid-cols-2 lg:grid-cols-3">
          {memberships.map((membership) => (
            <div
              key={membership.id}
              className="bg-gray-800 border border-gray-700 rounded-lg shadow-xl hover:shadow-2xl hover:scale-105 transform transition-all duration-300 p-8 text-center"
            >
              <h2 className="text-2xl font-bold text-blue-400 mb-4">
                {membership.type} Membership
              </h2>
              <p className="text-gray-400 mb-4">{membership.description}</p>
              <p className="text-xl font-semibold text-gray-200 mb-6">
                ${membership.price.toFixed(2)}
              </p>
              <button
                onClick={() => handleSelectPlan(membership.id, membership.type)}
                className="w-full py-2 bg-blue-600 hover:bg-blue-700 text-white font-semibold rounded-lg shadow-lg transition-transform transform hover:scale-105"
              >
                Upgrade
              </button>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default PremiumMembership;
