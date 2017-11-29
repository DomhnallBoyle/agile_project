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
    public partial class UserStoryDetails : BasePage
    {

        #region Private variables
        private UserStoryClient _client;

        #endregion

        #region Public variables

        public UserStory SelectedUserStory { get; set; }

   

        #endregion

        public UserStoryDetails(UserStory selectedUserStory)
        {
            InitializeComponent();
            CurrentPage = this.Title;
            DataContext = this;

            _client = new UserStoryClient();

            SelectedUserStory = selectedUserStory;

             UserStoryAcceptanceTests.ItemsSource = _client.GetAcceptanceTestsFromUserStory(SelectedUserStory.Project.Id, SelectedUserStory.Id);
        }

        #region Command methods

        public ICommand UpdateCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    AcceptanceTest acceptanceTest = ((AcceptanceTest)param);
                    _client.UpdateAcceptanceTest(SelectedUserStory.Project.Id, SelectedUserStory.Id, acceptanceTest);
                });
            }
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
