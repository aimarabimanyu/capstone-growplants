const Response = require("../model/Response");
const User = require("../model/User");
const httpStatus = require("http-status");

const getUser = async (req, res) => {
  const user = req.currentUser;
  const response = new Response.Success(false, null, user);
  res.json(response);
};

const updateUser = async (req, res) => {
  try {    
    const userId = req.currentUser._id;
    const userEmail = req.currentUser.email;

    await processFile(req, res);
    
    if (!req.file) {
      const response = new Response.Error(400, "Please upload a image!" );
      return res.status(httpStatus.BAD_REQUEST).json(response);
    }

    const ext = req.file.originalname.split('.').pop();
    if (ext !== "png" && ext !== "jpg" && ext !== "jpeg" && ext !== "PNG" && ext !== "JPG" && ext !== "JPEG") {
      const response = new Response.Error(400, "Only images are allowed" );
      return res.status(httpStatus.BAD_REQUEST).json(response);
    }

    const upload = new UserImages({
        userId: userId,
        email: userEmail,
    });
    const uploadSave = await upload.save();

    // Return response
    const response = new Response.Success(false, null, uploadSave);
    res.status(httpStatus.OK).json(response);

  } catch (error) {
    const response = new Response.Error(true, error.message);
    res.status(httpStatus.BAD_REQUEST).json(response);
  }
};

module.exports = { getUser, updateUser };
