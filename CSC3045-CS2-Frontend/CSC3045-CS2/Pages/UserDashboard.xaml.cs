using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;

namespace CSC3045_CS2.Pages
{
    /// <summary>
    /// Interaction logic for UserDashboard.xaml
    /// </summary>
    public partial class UserDashboard : BasePage
    {
        #region Private variables

        private ProjectClient _projectClient;
        private UserClient _userClient;

        #endregion

        #region Public variables

        public List<ProjectPermissions> ProjectList { get; set; }

        public ObservableCollection<String> AvailableSkills
        { get; set; }

        public ObservableCollection<String> MySelectedObjects
        { get; set; }

        #endregion

        public UserDashboard()
        { 
            InitializeComponent();
            CurrentPage = this.Title;
            DataContext = this;

            User user = ((User)Application.Current.Properties["user"]);
            UserLabel = user.GetFullName();
            string profileImageFileName;
            if (user.ProfilePicture != null)
            {
                profileImageFileName = user.ProfilePicture;
            }
            else
            {
                profileImageFileName = Properties.Settings.Default.DefaultProfileImage;
            }
            string profileImagePath = Properties.Settings.Default.ProfileImageDirectory + profileImageFileName;
            
            _projectClient = new ProjectClient();
            _userClient = new UserClient();

            InitialiseProjects();
        }

        #region Class methods

        private void InitialiseProjects()
        {
            try
            {
                List<Project> inboundProjects = _userClient.GetProjectsForUser(((User)Application.Current.Properties["user"]).Id);
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

        #endregion
    }
}
