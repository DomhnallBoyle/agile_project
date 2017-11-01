using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
using CSC3045_CS2.Pages;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;
using System;
using System.Collections.Generic;
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

        private List<UserStory> _backlog;

        #endregion

        #region Public Variables

        public string PageLabel { get; set; } = "Product Backlog";

        public string MarketValueLabel { get; set; } = "Market Value";
        
        public string BackButtonLabel { get; set; } = "Back";

        public string CreateStoryButtonLabel { get; set; } = "Create Story";

        #endregion

        public ProductBacklog()
        {
            InitializeComponent();
            DataContext = this;
            _client = new UserStoryClient();

            try
            {
                // TODO:: Get project ID from current project via constructor
                _backlog = _client.GetUserStories(0);
            }
            catch (RestResponseErrorException ex)
            {
                MessageBox.Show(ex.Message);
            }

            StoryList.ItemsSource = _backlog;
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
                    Page register = new Register();

                    NavigationService.GetNavigationService(this).Navigate(register);
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
                    Page createUserStoryPage = new CreateUserStory();

                    NavigationService.GetNavigationService(this).Navigate(createUserStoryPage);
                });
            }
        }

        #endregion
    }
}
