using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
using CSC3045_CS2.Pages;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;
using System.Collections.ObjectModel;
using System.Linq;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Navigation;

namespace CSC3045_CS2
{
    /// <summary>
    /// Interaction logic for ProductBacklog.xaml
    /// </summary>
    public partial class ProductBacklog : BasePage
    {
        #region Private Variables

        private UserStoryClient _client;

        private ObservableCollection<UserStory> _backlog = new ObservableCollection<UserStory>();

        #endregion

        #region Public Variables

        public Project CurrentProject { get; set; }

        public Permissions Permissions { get; set; }

        public PermissonDragHandler PermissonDragHandler { get; set; }

        public ObservableCollection<UserStory> Backlog { get; set; }

        public string PageLabel { get; set; } = "Product Backlog";

        public string MarketValueLabel { get; set; } = "Market Value";

        public string BackButtonLabel { get; set; } = "Back";

        public string CreateStoryButtonLabel { get; set; } = "Create Story";

        #endregion

        public ProductBacklog(Project project)
        {
            InitializeComponent();
            CurrentPage = this.Title;
            DataContext = this;
            _client = new UserStoryClient();

            CurrentProject = project;

            Permissions = new Permissions((User)Application.Current.Properties["user"], project);
            PermissonDragHandler = new PermissonDragHandler(Permissions.ProductOwner);

            try
            {
                var sortedOC = from item in _client.GetUserStories(project.Id) orderby item.Index select item;
                Backlog = new ObservableCollection<UserStory>(sortedOC.ToList());
            }
            catch (RestResponseErrorException ex)
            {
                MessageBoxUtil.ShowErrorBox(ex.Message);
            }
        }

        #region ICommands

        /// <summary>
        /// Returns to the project dashboard
        /// </summary>
        /// 
        public ICommand BackCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page projectDashboard = new ProjectDashboard(CurrentProject);

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
                        MessageBoxUtil.ShowSuccessBox("User stories updated successfully");
                    }
                    catch (RestResponseErrorException ex)
                    {
                        MessageBoxUtil.ShowErrorBox(ex.Message);
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
                    Page createUserStoryPage = new CreateUserStory(CurrentProject);

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

        private void CreateStoryButton_Click(object sender, RoutedEventArgs e)
        {

        }
    }


}
