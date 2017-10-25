using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;
using System;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Navigation;

namespace CSC3045_CS2.Pages
{
    /// <summary>
    /// Interaction logic for Login.xaml
    /// </summary>
    public partial class Login : Page
    {
        #region Private Variables

        private AuthenticationClient _client;

        #endregion

        #region Public Variables

        public String LoginTitle { get; set; } = "Login";

        public String LoginUsernameLabel { get; set; } = "Username: ";

        public String LoginPasswordLabel { get; set; } = "Password: ";

        public String UsernameTextContent { get; set; }
        
        public String LoginButtonText { get; set; } = "Login";

        public String RegisterButtonText { get; set; } = "Register";

        #endregion

        public Login()
        {
            InitializeComponent();
            DataContext = this;
            _client = new AuthenticationClient();
        }

        #region Command methods

        public ICommand LoginCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Account account = new Account();
                    account.Username = UsernameTextContent;
                    account.Password = PasswordBox.Password.ToString();

                    try
                    {
                        _client.Login(account);
                        Page test = new test();

                        NavigationService.GetNavigationService(this).Navigate(test);
                    }
                    catch (RestResponseErrorException ex)
                    {
                        MessageBox.Show(ex.Message);
                    }
                });
            }
        }

        public ICommand NavigateToRegistrationCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page registrationPage = new Register();

                    NavigationService.GetNavigationService(this).Navigate(registrationPage);
                });
            }
        }

        #endregion
    }
}
