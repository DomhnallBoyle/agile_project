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
using System.Text;

namespace CSC3045_CS2.Pages
{
    public partial class Register : Page
    {
        #region Private Variables 
        private AuthenticationClient _client;
        private BitmapImage _profileImage;
        private String _warningMessage;
        private Style _invalidTextBoxStyle;
        private Style _validTextBoxStyle;
        private Style _invalidPasswordBoxStyle;
        private Style _validPasswordBoxStyle;
        #endregion

        public Register()
        {
            InitializeComponent();
            DataContext = this;
            _client = new AuthenticationClient();

            _profileImage = new BitmapImage(new Uri(Properties.Settings.Default.ProfileImageDirectory + Properties.Settings.Default.DefaultProfileImage, UriKind.Absolute));
            ImageBrush profileButtonBackground = new ImageBrush();
            profileButtonBackground.ImageSource = _profileImage;
            ProfileButton.Background = profileButtonBackground;
            _invalidTextBoxStyle = FindResource("InvalidTextBox") as Style;
            _validTextBoxStyle = FindResource("DefaultTextBox") as Style;
            _validPasswordBoxStyle = FindResource("DefaultPasswordBox") as Style;
            _invalidPasswordBoxStyle = FindResource("InvalidPasswordBox") as Style;
        }

        #region Command Methods
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
            if (CheckFields())
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
            else
            {
                MessageBoxUtil.ShowWarningBox(_warningMessage);
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

        #endregion

        #region Class Methods
        public void SaveImage( BitmapImage image, string filePath)
        {
            BitmapEncoder encoder = new PngBitmapEncoder();
            encoder.Frames.Add(BitmapFrame.Create(image));

            using (var fileStream = new FileStream(filePath, FileMode.Create))
            {
                encoder.Save(fileStream);
            }
        }
        #endregion

        #region Form Validation
        /// <summary>
        /// Check the 2 passwords match 
        /// </summary>
        /// <param name="mainPasswordBox"></param>
        /// <param name="confirmPasswordBox"></param>
        /// <returns></returns>
        private Boolean CheckPasswordsMatch()
        {
            return PasswordTextBox.Password.ToString() == ConfirmPasswordTextBox.Password.ToString();
        }

        private bool CheckFields()
        {
            bool valid = true;
            StringBuilder sb = new StringBuilder();
            if (String.IsNullOrEmpty(FirstnameTextBox.Text))
            {
                FirstnameTextBox.Style = _invalidTextBoxStyle;
                valid = false;
                sb.Append("You must enter a first name\n");
            }
            else
            {
                FirstnameTextBox.Style = _validTextBoxStyle;
            }

            if (String.IsNullOrEmpty(SurnameTextBox.Text))
            {
                SurnameTextBox.Style = _invalidTextBoxStyle;
                valid = false;
                sb.Append("You must enter a surname\n");
            }
            else
            {
                SurnameTextBox.Style = _validTextBoxStyle;
            }

            if (String.IsNullOrEmpty(EmailTextBox.Text))
            {
                EmailTextBox.Style = _invalidTextBoxStyle;
                valid = false;
                sb.Append("You must enter an email\n");
            }
            else
            {
                EmailTextBox.Style = _validTextBoxStyle;
            }

            if (String.IsNullOrEmpty(PasswordTextBox.Password.ToString()))
            {
                PasswordTextBox.Style = _invalidPasswordBoxStyle;
                valid = false;
                sb.Append("You must enter a password\n");
            }
            else
            {
                PasswordTextBox.Style = _validPasswordBoxStyle;
            }

            if (String.IsNullOrEmpty(ConfirmPasswordTextBox.Password.ToString()))
            {
                ConfirmPasswordTextBox.Style = _invalidPasswordBoxStyle;
                valid = false;
                sb.Append("You must confirm your password\n");
            }
            else
            {
                ConfirmPasswordTextBox.Style = _validPasswordBoxStyle;
            }

            if (!CheckPasswordsMatch())
            {
                PasswordTextBox.Style = _invalidPasswordBoxStyle;
                ConfirmPasswordTextBox.Style = _invalidPasswordBoxStyle;
                valid = false;
                sb.Append("Passwords must match\n");
            }

            _warningMessage = sb.ToString();
            return valid;
        }

        #endregion
    }
}