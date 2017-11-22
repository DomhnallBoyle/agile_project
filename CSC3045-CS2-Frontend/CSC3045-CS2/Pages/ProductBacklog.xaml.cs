using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
using CSC3045_CS2.Pages;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace CSC3045_CS2
{
    /// <summary>
    /// Interaction logic for ProductBacklog.xaml
    /// </summary>
    public partial class ProductBacklog : Page
    {
        #region Private Variables

        private UserStoryClient _client;

        private Project _currentProject;

        #endregion

        #region Public Variables

        public Permissions Permissions { get; set; }

        public ObservableCollection<UserStory> Backlog { get; set; }

        public string PageLabel { get; set; } = "Product Backlog";

        public string MarketValueLabel { get; set; } = "Market Value";

        public string BackButtonLabel { get; set; } = "Back";

        public string CreateStoryButtonLabel { get; set; } = "Create Story";

        #endregion

        public ProductBacklog(Project project)
        {
            InitializeComponent();
            DataContext = this;
            _client = new UserStoryClient();

            _currentProject = project;

            Permissions = new Permissions((User)Application.Current.Properties["user"], project);

            try
            {
                var sortedOC = from item in _client.GetUserStories(project.Id) orderby item.Index select item;
                Backlog = new ObservableCollection<UserStory>(sortedOC.ToList());
            }
            catch (RestResponseErrorException ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        #region commands

        /// <summary>
        /// Returns to the project dashboard
        /// </summary>
        public ICommand BackCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page projectDashboard = new ProjectDashboard(_currentProject);

                     NavigationService.GetNavigationService(this).Navigate(projectDashboard);

                });
            }
        }

        /// <summary>
        /// Changes order of user stories before
        /// sending the updated list to the backend
        /// </summary>
        public ICommand SaveOrder
        {
            get
            {
                return new RelayCommand(param =>
                {
                    for (int i = 0; i < Backlog.Count; i++)
                    {
                        Backlog[i].Index = i + 1;
                    }

                    try
                    {
                        _client.SaveOrder(Backlog.ToList());
                        MessageBox.Show("User stories updated successfully");
                    }
                    catch (RestResponseErrorException)
                    {
                        MessageBox.Show("Error saving story order");
                    }
                });
            }
        }

        /// <summary>
        /// Navigates to the Story creation page
        /// </summary>
        public ICommand CreateStoryCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page createUserStoryPage = new CreateUserStory(_currentProject);

                    NavigationService.GetNavigationService(this).Navigate(createUserStoryPage);
                });
            }
        }

        public ICommand ViewDetailsCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    UserStory selectedStory = (UserStory)param;
                    Page userStoryDetailsPage = new UserStoryDetails(selectedStory);

                    NavigationService.GetNavigationService(this).Navigate(userStoryDetailsPage);
                });
            }
        }

        #endregion

        private void StoryList_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            UserStory selectedStory = (UserStory)StoryList.SelectedItem;
            Page userStoryDetailsPage = new UserStoryDetails(selectedStory);

            NavigationService.GetNavigationService(this).Navigate(userStoryDetailsPage);
        }
    }


}
