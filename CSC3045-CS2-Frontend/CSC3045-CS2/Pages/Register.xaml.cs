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
        private string profiler = "buckie.jpg";
        public Register()
        {
            InitializeComponent();
            DataContext = this;
            _client = new AuthenticationClient();
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
                User user = new User(FirstnameTextBox.Text, SurnameTextBox.Text, EmailTextBox.Text, profiler, roles);
                Account account = new Account(user, PasswordTextBox.Password.ToString());

                try
                {
                    this._client.Register(account);

                    MessageBox.Show("Registration successful!", "Success");
                    Page loginPage = new Login();

                    NavigationService.GetNavigationService(this).Navigate(loginPage);
                }
                catch (RestResponseErrorException ex)
                {
                    MessageBox.Show(ex.Message, "Failure");
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


            // Get the selected file name and display in a TextBox 
            if (result == true)
            {
                // Open document 
                string filename = dlg.FileName;
                BitmapImage profileImage  = new BitmapImage(new Uri(filename, UriKind.Absolute));


                string path = Directory.GetCurrentDirectory();
                string newPath = Path.GetFullPath(Path.Combine(path, @"..\..\profiles\"));
                SaveImage(profileImage, newPath + "buckie.jpg");
                profile.Source = new BitmapImage(new Uri(newPath + "buckie.jpg", UriKind.RelativeOrAbsolute));
                ProfileButton.Content = filename;
            }
        }

        public void SaveImage( BitmapImage image, string filePath)
        {
            BitmapEncoder encoder = new PngBitmapEncoder();
            encoder.Frames.Add(BitmapFrame.Create(image));

            using (var fileStream = new System.IO.FileStream(filePath, System.IO.FileMode.Create))
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
        private Boolean CheckRequiredValues(TextBox textBox)
        {
            if (string.IsNullOrEmpty(textBox.Text))
            {
                String text = "Field cannot be empty.";
                MessageBox.Show(text, "Warning");
                textBox.Background = Brushes.Red;
                return false;
            }
            else
            {
                textBox.Background = Brushes.White;
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
                String text = "Password cannot be empty.";
                MessageBox.Show(text, "Warning");
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
                String text = "Passwords don't match.";
                MessageBox.Show(text, "Warning");
                mainPasswordBox.Background = Brushes.Red;
                confirmPasswordBox.Background = Brushes.Red;

                return false;
            }
            else
            {
                mainPasswordBox.Background = Brushes.White;
                return true;
            }
        }

        /// <summary>
        /// Method to Check validation, returns true if all conditions are met, false otherwise
        /// </summary>
        /// <returns></returns>
        private Boolean CheckValidation()
        {
            return CheckRequiredValues(FirstnameTextBox) &&
             CheckRequiredValues(EmailTextBox) &&
             CheckPasswordNotEmpty(PasswordTextBox) &&
             CheckPasswordNotEmpty(ConfirmPasswordTextBox) &&
             CheckPasswordsMatch(PasswordTextBox, ConfirmPasswordTextBox);
        }
    }
}
