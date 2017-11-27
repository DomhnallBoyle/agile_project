using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;
using System.Collections.Generic;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Windows.Navigation;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows;

namespace CSC3045_CS2.Pages
{
    /// <summary>
    /// Interaction logic for UserStoryDetails.xaml
    /// </summary>
    public partial class UserStoryDetails : Page
    {

        #region Private variables
        private UserStoryClient _client;

        #endregion

        #region Public variables

        public UserStory SelectedUserStory { get; set; }

        public string UserLabel { get; set; }

        public string image { get; set; }

        public string CurrentPage { get; set; }

        #endregion

        public UserStoryDetails(UserStory selectedUserStory)
        {
            InitializeComponent();
            generateHeader();
            DataContext = this;

            _client = new UserStoryClient();

            SelectedUserStory = selectedUserStory;

             UserStoryAcceptanceTests.ItemsSource = _client.GetAcceptanceTestsFromUserStory(SelectedUserStory.Id);
        }

        #region Command methods

        public ICommand UpdateCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    AcceptanceTest acceptanceTest = ((AcceptanceTest)param);
                    _client.UpdateAcceptanceTest(acceptanceTest);
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
        public void generateHeader()
        {
            User user = ((User)Application.Current.Properties["user"]);
            UserLabel = user.GetFullName();
            image = Properties.Settings.Default.ProfileImageDirectory + user.ProfilePicture;
            CurrentPage = this.Title;
        }


        public ICommand GoToCreateAcceptanceTestCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page createAcceptanceTest = new CreateAcceptanceTest(SelectedUserStory);

                    NavigationService.GetNavigationService(this).Navigate(createAcceptanceTest);
                });
            }
        }

        public ICommand GoToProductBacklogCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page productBacklog = new ProductBacklog(SelectedUserStory.Project);

                    NavigationService.GetNavigationService(this).Navigate(productBacklog);
                });
            }
        }
        #endregion

    }
}
