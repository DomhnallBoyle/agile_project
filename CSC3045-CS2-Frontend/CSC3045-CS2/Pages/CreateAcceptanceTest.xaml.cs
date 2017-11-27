using System;
using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;

using System.Text.RegularExpressions;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Navigation;
namespace CSC3045_CS2.Pages
{
    /// <summary>
    /// Interaction logic for CreateAcceptanceTest.xaml
    /// </summary>
    public partial class CreateAcceptanceTest : Page
    {
        #region Private Variables

        private UserStoryClient _client;

        private UserStory _currentUserStory;

        #endregion

        public string UserLabel { get; set; }
        public string image { get; set; }
        public string CurrentPage { get; set; }

        public CreateAcceptanceTest(UserStory userStory)
        {
            InitializeComponent();
            generateHeader();
            DataContext = this;
            _client = new UserStoryClient();

            _currentUserStory = userStory;
        }
        public void generateHeader()
        {
            User user = ((User)Application.Current.Properties["user"]);
            UserLabel = user.GetFullName();
            image = Properties.Settings.Default.ProfileImageDirectory + user.ProfilePicture;
            CurrentPage = this.Title;
        }

        #region Command Methods

        public ICommand CancelCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page userStoryDetail = new UserStoryDetails(_currentUserStory);

                    NavigationService.GetNavigationService(this).Navigate(userStoryDetail);
                });
            }
        }

        public ICommand CreateCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    AcceptanceTest acceptanceTest = new AcceptanceTest(GivenTextBox.Text, WhenTextBox.Text, ThenTextBox.Text, _currentUserStory);

                    try
                    {
                        _client.CreateAcceptanceTest(acceptanceTest);

                        MessageBoxUtil.ShowSuccessBox("Acceptance Test Creation Successful!");

                        Page userStoryDetails = new UserStoryDetails(_currentUserStory);

                        NavigationService.GetNavigationService(this).Navigate(userStoryDetails);
                    }
                    catch (RestResponseErrorException ex)
                    {
                        MessageBoxUtil.ShowErrorBox(ex.Message);
                    }
                });
            }
        }
        public ICommand LogoutCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    if (Application.Current.Properties.Contains("user"))
                    {
                        Application.Current.Properties.Remove("user");
                    }

                    Page loginPage = new Login();

                    NavigationService.GetNavigationService(this).Navigate(loginPage);
                });
            }
        }
        #endregion
    }
}
