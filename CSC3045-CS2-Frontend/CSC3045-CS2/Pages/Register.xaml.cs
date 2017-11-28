using System;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Navigation;
using CSC3045_CS2.Exception;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;
using CSC3045_CS2.Models;
using System.Windows.Media.Imaging;
using System.IO;

namespace CSC3045_CS2.Pages
{
    public partial class Register : Page
    {
        private AuthenticationClient _client;
        private BitmapImage _profileImage;

        public Register()
        {
            InitializeComponent();
            DataContext = this;
            _client = new AuthenticationClient();

            _profileImage = new BitmapImage(new Uri(Properties.Settings.Default.ProfileImageDirectory + Properties.Settings.Default.DefaultProfileImage, UriKind.Absolute));
            ImageBrush profileButtonBackground = new ImageBrush();
            profileButtonBackground.ImageSource = _profileImage;
            ProfileButton.Background = profileButtonBackground;

        }

        /// <summary>
        /// Method for Creating The action that occurs on button click, calls the checkValidationMethod, if
        /// it returns true then attempt to register to the backend. If the response returned is equal to 
        /// Succesfully registered (ie 200) then create a new page, if its not
        /// there will be further feedback indicating what is wrong, also caught is the server not started error
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        public void RegisterButton_Click(Object sender, EventArgs e)
        {
            if (CheckValidation())
            {
                Roles roles = new Roles(ProductOwnerCheckBox.IsChecked.Value, ScrumMasterCheckBox.IsChecked.Value, DeveloperCheckBox.IsChecked.Value);
                string profileImagePath = null;
                if (_profileImage != null)
                {
                    profileImagePath = EmailTextBox.Text + Properties.Settings.Default.DefaultProfileImageFileExtension;
                }
                else
                {
                    profileImagePath = Properties.Settings.Default.DefaultProfileImage;
                }
                User user = new User(FirstnameTextBox.Text, SurnameTextBox.Text, EmailTextBox.Text, profileImagePath , roles);
                Account account = new Account(user, PasswordTextBox.Password.ToString());

                try
                {
                    Account returnedAccount = this._client.Register(account);

                    MessageBoxUtil.ShowSuccessBox("Registration successful!");

                    Page loginPage = new Login();
                    if(_profileImage != null)
                    {
                        SaveImage(_profileImage, Properties.Settings.Default.ProfileImageDirectory + returnedAccount.User.Email + Properties.Settings.Default.DefaultProfileImageFileExtension);
                    }
                    
                    NavigationService.GetNavigationService(this).Navigate(loginPage);
                }
                catch (RestResponseErrorException ex)
                {
                    MessageBoxUtil.ShowErrorBox(ex.Message);
                }
            }
        }

        public void ProfileButton_Click(Object sender, EventArgs e)
        {
            // Create OpenFileDialog 
            Microsoft.Win32.OpenFileDialog dlg = new Microsoft.Win32.OpenFileDialog();

            // Set filter for file extension and default file extension 
            dlg.DefaultExt = ".png";
            dlg.Filter = "JPEG Files (*.jpeg)|*.jpeg|PNG Files (*.png)|*.png|JPG Files (*.jpg)|*.jpg|GIF Files (*.gif)|*.gif";

            // Display OpenFileDialog by calling ShowDialog method 
            Nullable<bool> result = dlg.ShowDialog();

            if (result == true )
            {
                string filename = dlg.FileName;
                FileInfo fi = new FileInfo(dlg.FileName);
                long fileSize = fi.Length;
                Console.WriteLine(fileSize);

                //limiting file size upload to 1mb for performance reasons
                if (fileSize < (1000000))
                {
                    _profileImage = new BitmapImage(new Uri(filename, UriKind.Absolute));
                    ImageBrush profileButtonBackground = new ImageBrush();
                    profileButtonBackground.ImageSource = _profileImage;
                    ProfileButton.Background = profileButtonBackground;
                }
                else
                {
                    MessageBox.Show("File Size Too Large, Max File Size Should be 1Mb");
                }
            }
        }

        public void SaveImage( BitmapImage image, string filePath)
        {
            BitmapEncoder encoder = new PngBitmapEncoder();
            encoder.Frames.Add(BitmapFrame.Create(image));

            using (var fileStream = new FileStream(filePath, FileMode.Create))
            {
                encoder.Save(fileStream);
            }
        }   

        /// <summary>
        /// Navigates the user back to the Login Page
        /// </summary>
        public ICommand BackCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page loginPage = new Login();

                    NavigationService.GetNavigationService(this).Navigate(loginPage);
                });
            }
        }

        /// <summary>
        /// Method for checking if the required values for the User Registration Form is filled in
        /// if the Form isnt filled correctly, text box is highlighted
        /// </summary>
        /// <param name="textBox"></param>
        /// <returns></returns>
        private Boolean CheckRequiredValues(TextBox textBox, TextBlock textBlock)
        {
            if (string.IsNullOrEmpty(textBox.Text))
            {
                MessageBoxUtil.ShowWarningBox("Field cannot be empty.");
                textBox.Style = (Style)FindResource("InvalidTextBox");
                textBlock.Style = (Style)FindResource("InvalidWatermark");

                return false;
            }
            else
            {
                textBox.Style = (Style)FindResource("DefaultTextBox");

                textBlock.Style = (Style)FindResource("Watermark");
                return true;
            }
        }

        /// <summary>
        /// Method for checking if password isnt empty, if it is turn the  box red
        /// </summary>
        /// <param name="passwordBox"></param>
        /// <returns></returns>
        private Boolean CheckPasswordNotEmpty(PasswordBox passwordBox)
        {
            if (passwordBox.Password.ToString() == "")
            {
                MessageBoxUtil.ShowWarningBox("Password cannot be empty.");
                passwordBox.Style = (Style)FindResource("InvalidPasswordBox");
                return false;
            }
            else
            {
                passwordBox.Style = (Style)FindResource("DefaultPasswordBox");
                return true;
            }
        }

        /// <summary>
        /// Check the 2 passwords match 
        /// </summary>
        /// <param name="mainPasswordBox"></param>
        /// <param name="confirmPasswordBox"></param>
        /// <returns></returns>
        private Boolean CheckPasswordsMatch(PasswordBox mainPasswordBox, PasswordBox confirmPasswordBox)
        {
            if (mainPasswordBox.Password.ToString() != confirmPasswordBox.Password.ToString())
            {
                MessageBoxUtil.ShowWarningBox("Passwords don't match.");
                mainPasswordBox.Style = (Style)FindResource("InvalidPasswordBox");
                confirmPasswordBox.Style = (Style)FindResource("InvalidPasswordBox");

                return false;
            }
            else
            {
                mainPasswordBox.Style = (Style)FindResource("DefaultPasswordBox");
                confirmPasswordBox.Style = (Style)FindResource("DefaultPasswordBox");
                return true;
            }
        }

        /// <summary>
        /// Method to Check validation, returns true if all conditions are met, false otherwise
        /// </summary>
        /// <returns></returns>
        private Boolean CheckValidation()
        {
            return CheckRequiredValues(FirstnameTextBox, FirstnameTextBlock) &&
             CheckRequiredValues(SurnameTextBox, SurnameTextBlock) &&
             CheckRequiredValues(EmailTextBox, EmailTextBlock) &&
             CheckPasswordNotEmpty(PasswordTextBox) &&
             CheckPasswordNotEmpty(ConfirmPasswordTextBox) &&
             CheckPasswordsMatch(PasswordTextBox, ConfirmPasswordTextBox);
        }
    }
}
