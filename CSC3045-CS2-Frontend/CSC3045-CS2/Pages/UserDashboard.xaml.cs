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

        private ProjectClient _client;

        #endregion

        #region Public variables

        public List<ProjectPermissions> ProjectList { get; set; }

        public String UserLabel { get; set; }

        #endregion

        public UserDashboard()
        {
            InitializeComponent();
            DataContext = this;

            UserLabel = ((User)Application.Current.Properties["user"]).GetFullName();

            _client = new ProjectClient();

            InitialiseProjects();
        }

        #region Class methods

        private void InitialiseProjects()
        {
            try
            {
                List<Project> inboundProjects = _client.GetProjectsForUser(((User)Application.Current.Properties["user"]).Id);
                List<ProjectPermissions> projectList = new List<ProjectPermissions>();

                foreach (Project project in inboundProjects)
                {
                    projectList.Add(new ProjectPermissions(project, (User)Application.Current.Properties["user"]));
                }

                ProjectList = projectList;
            }
            catch (RestResponseErrorException ex)
            {
                if (ex.StatusCode == System.Net.HttpStatusCode.NotFound)
                {
                    MessageBoxUtil.ShowInfoBox(ex.Message);
                }
                else
                {
                    MessageBoxUtil.ShowErrorBox(ex.Message);
                }
            }
        }

        #endregion

        #region Command and Event methods

        private void ProjectListBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            Project selectedProject = ((ProjectPermissions)ProjectListBox.SelectedItem).Project;
            Page projectDashboardPage = new ProjectDashboard(selectedProject);

            NavigationService.GetNavigationService(this).Navigate(projectDashboardPage);
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
