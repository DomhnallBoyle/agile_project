using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
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

namespace CSC3045_CS2.Pages
{
    /// <summary>
    /// Interaction logic for UserDashboard.xaml
    /// </summary>
    public partial class UserDashboard : Page
    {
        #region Private variables

        private List<Project> _projectList;
        private TextBlock _projectName, _projectDescription;
        private Button _toProjectDashboard;
        private ProjectClient _client;

        #endregion

        #region Public variables

        public String UserLabel { get; set; }

        #endregion

        public UserDashboard()
        {
            InitializeComponent();
            DataContext = this;

            UserLabel = ((User)Application.Current.Properties["user"]).FullName;

            _client = new ProjectClient();

            GetProjects();
        }

        #region Class methods

        private void AddProjectsToUIList()
        {
            for (int i=0; i<_projectList.Count; i++)
            {
                _projectName = new TextBlock
                {
                    Text = _projectList[i].Name
                };
                _projectDescription = new TextBlock
                {
                    Text = _projectList[i].Description
                };
                _toProjectDashboard = new Button
                {
                    Content = "Go to Project",
                    Command = GoToCommand,
                    CommandParameter = i
                };

                ProjectNamePanel.Children.Add(_projectName);
                DetailsPanel.Children.Add(_projectDescription);
                ButtonPanel.Children.Add(_toProjectDashboard);
            }
        }

        private void GetProjects()
        {
            try
            {
                _projectList = _client.GetProjectsForUser(((User)Application.Current.Properties["user"]).Id);
                AddProjectsToUIList();
            }
            catch (RestResponseErrorException ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        #endregion

        #region Command methods

        public ICommand GoToCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    try
                    {
                        var projectIndex = (int) param;
                        Page projectDashboardPage = new ProjectDashboard(_projectList, projectIndex);

                        NavigationService.GetNavigationService(this).Navigate(projectDashboardPage);
                    }
                    catch (RestResponseErrorException ex)
                    {
                        MessageBox.Show(ex.Message);
                    }
                });
            }
        }


        public ICommand NavigateToCreateProjectCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page createProjectPage = new CreateProject();

                    NavigationService.GetNavigationService(this).Navigate(createProjectPage);
                });
            }
        }

        #endregion
    }
}
