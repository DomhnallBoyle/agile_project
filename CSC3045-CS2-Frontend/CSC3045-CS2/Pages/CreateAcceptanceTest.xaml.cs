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

        #region Public Variables

        public String GivenLabel { get; set; } = "Given:";

        public String WhenLabel { get; set; } = "When:";

        public String ThenLabel { get; set; } = "Then:";

        public String GivenTextContent { get; set; }

        public String WhenTextContent { get; set; }

        public String ThenTextContent { get; set; }

        public String CancelButtonText { get; set; } = "Cancel";

        public String CreateButtonText { get; set; } = "Create";

        #endregion

        public CreateAcceptanceTest(UserStory userStory)
        {
            InitializeComponent();
            DataContext = this;
            _client = new UserStoryClient();

            _currentUserStory = userStory;
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
                    AcceptanceTest acceptanceTest = new AcceptanceTest(GivenTextContent, WhenTextContent, ThenTextContent, _currentUserStory);

                    try
                    {
                        _client.CreateAcceptanceTest(acceptanceTest);

                        MessageBox.Show("Acceptance Test Creation Successful!");

                        Page userStoryDetails = new UserStoryDetails(_currentUserStory);

                        NavigationService.GetNavigationService(this).Navigate(userStoryDetails);
                    }
                    catch (RestResponseErrorException ex)
                    {
                        MessageBox.Show(ex.Message);
                    }
                });
            }
        }
        #endregion
    }
}
