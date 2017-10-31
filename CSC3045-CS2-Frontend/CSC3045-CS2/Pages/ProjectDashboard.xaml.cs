using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
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
    /// Interaction logic for ProjectDashboard.xaml
    /// </summary>
    public partial class ProjectDashboard : Page
    {
        #region Private Variables

        private List<Project> _projects;
        private int _currentProjectNumber;
        private MenuItem _projectName;

        #endregion

        public ProjectDashboard(List<Project> projects, int currentProjectNumber)
        {
            InitializeComponent();
            DataContext = this;
            this._projects = projects;
            this._currentProjectNumber = currentProjectNumber;
            ProjectDropDownButton.Content = this._projects[currentProjectNumber].ProjectName;
            addProjectstoList();
        }

        #region Class methods

        private void addProjectstoList()
        {
            for (int i = 0; i < _projects.Count; i++)
            {
                _projectName = new MenuItem
                {
                    Header = _projects[i].ProjectName,
                    Command = goToCommand,
                    CommandParameter = i
                };

                if (this._currentProjectNumber == i)
                {
                    _projectName.Background = Brushes.Wheat;
                    _projectName.IsChecked = true;
                }

                menuItems.Items.Add(_projectName);
            }
        }

        #endregion

        #region Command methods

        public ICommand ProjectDropDown
        {
            get
            {
                return new RelayCommand(param =>
                {
                    ProjectDropDownButton.ContextMenu.IsEnabled = true;
                    ProjectDropDownButton.ContextMenu.PlacementTarget = ProjectDropDownButton;
                    ProjectDropDownButton.ContextMenu.Placement = System.Windows.Controls.Primitives.PlacementMode.Bottom;
                    ProjectDropDownButton.ContextMenu.IsOpen = true;
                });
            }
        }

        public ICommand goToCommand

        {
            get
            {
                return new RelayCommand(param =>
                {
                    try
                    {
                        var projectNumber = ((int)param);
                        Page projectDashboard = new ProjectDashboard(_projects, projectNumber);

                        NavigationService.GetNavigationService(this).Navigate(projectDashboard);
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
