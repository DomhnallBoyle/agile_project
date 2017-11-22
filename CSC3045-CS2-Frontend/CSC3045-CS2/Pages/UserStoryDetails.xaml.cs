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

        private UserStoryClient _client;

        private UserStory _selectedUserStory;

        public bool? IsChecked { get; set; }

        public UserStory SelectedUserStory
        {
            get { return _selectedUserStory; }
            set
            {
                _selectedUserStory = value;
                OnPropertyChanged();
            }
        }
        public UserStoryDetails(UserStory selectedUserStory)
        {
           InitializeComponent();

            DataContext = this;

            _client = new UserStoryClient();

            SelectedUserStory = selectedUserStory;

            List<AcceptanceTest> acceptanceTest = new List<AcceptanceTest>();

            acceptanceTest.Add(new AcceptanceTest("given", "when", "then", _selectedUserStory));

            acceptanceTest.Add(new AcceptanceTest("given1", "when2", "then3", _selectedUserStory, true));

            UserStoryAcceptanceTests.ItemsSource = acceptanceTest;

           // UserStoryAcceptanceTests.ItemsSource = _client.GetAcceptanceTestsFromUserStory(_selectedUserStory.Id);
        }


        public ICommand UpdateCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    AcceptanceTest acceptanceTest = ((AcceptanceTest)param);
                    // _client.UpdateAcceptanceTest(acceptanceTest);
                    MessageBox.Show(acceptanceTest.Given);
                    MessageBox.Show(acceptanceTest.Then);
                    MessageBox.Show(acceptanceTest.When);
                    MessageBox.Show(acceptanceTest.Completed.ToString());
                    MessageBox.Show(acceptanceTest.Id.ToString());
                    MessageBox.Show(acceptanceTest.UserStory.Id.ToString());
                });
            }
        }
        #region Command methods


        public ICommand GoToCreateAcceptanceTestCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page createAcceptanceTest = new CreateAcceptanceTest(_selectedUserStory);

                    NavigationService.GetNavigationService(this).Navigate(createAcceptanceTest);
                });
            }
        }
        #endregion

        private void OnPropertyChanged([CallerMemberName] string propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }

        public event PropertyChangedEventHandler PropertyChanged;

    }
}
