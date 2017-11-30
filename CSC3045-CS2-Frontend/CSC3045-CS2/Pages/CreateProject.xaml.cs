using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;
using System;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Navigation;

namespace CSC3045_CS2.Pages
{
/// <summary>
/// Interaction logic for CreateProject.xaml
/// </summary>
public partial class CreateProject : BasePage
{
    #region Private Variables

    private ProjectClient _client;

    private String _warningMessage;

    private Style _invalidTextBoxStyle;
    private Style _validTextBoxStyle;

    private User _currentUser;

    #endregion

    #region Public Variables

    public User CurrentUser
    {
        get { return _currentUser; }
    }

    #endregion

    public CreateProject()
    {
        InitializeComponent();
        CurrentPage = this.Title;
        DataContext = this;

        _client = new ProjectClient();
        _currentUser = (User)Application.Current.Properties["user"];

        PageSetup();
    }

    #region Class Methods

    private void PageSetup()
    {
        _invalidTextBoxStyle = FindResource("InvalidTextBox") as Style;
        _validTextBoxStyle = FindResource("DefaultTextBox") as Style;
    }

    private bool CheckFields()
    {
        bool valid = true;
        StringBuilder sb = new StringBuilder();
        if (String.IsNullOrEmpty(ProjectNameTextBox.Text))
        {
            ProjectNameTextBox.Style = _invalidTextBoxStyle;
            valid = false;
            sb.Append("You must enter a project name\n");
        }
        else if(ProjectNameTextBox.Text.Length >= 255)
        {
                ProjectNameTextBox.Style = _invalidTextBoxStyle;
                valid = false;
                sb.Append("Project name must be less than 255 characters\n");
        }
        else
        {
            ProjectNameTextBox.Style = _validTextBoxStyle;
        }

        if (String.IsNullOrEmpty(ProjectDescriptionTextBox.Text))
        {
            ProjectDescriptionTextBox.Style = _invalidTextBoxStyle;
            valid = false;
            sb.Append("You must enter a project description\n");
        }
        else if (ProjectDescriptionTextBox.Text.Length >= 500)
        {
                ProjectDescriptionTextBox.Style = _invalidTextBoxStyle;
            valid = false;
            sb.Append("Project description must be less than 500 characters\n");
        }
        else
        {
            ProjectDescriptionTextBox.Style = _validTextBoxStyle;
        }

        _warningMessage = sb.ToString();
        return valid;
    }

    #endregion

    #region Command Methods

    public ICommand CreateProjectCommand
    {
        get
        {
            return new RelayCommand(param =>
            {
                if (CheckFields())
                {
                    Project project = new Project(CurrentUser, ProjectNameTextBox.Text, ProjectDescriptionTextBox.Text);
                    project.Manager = (User)Application.Current.Properties["user"];

                    try
                    {
                        _client.CreateProject(project);

                        MessageBoxUtil.ShowSuccessBox("Project creation successful!");

                        Page userDashboard = new UserDashboard();

                        NavigationService.GetNavigationService(this).Navigate(userDashboard);
                    }
                    catch (RestResponseErrorException ex)
                    {
                        MessageBoxUtil.ShowErrorBox(ex.Message);
                    }
                }
                else
                {
                    MessageBoxUtil.ShowWarningBox(_warningMessage);
                }
            });
        }
    }

    public ICommand CancelCommand
    {
        get
        {
            return new RelayCommand(param =>
            {
                Page userDashboard = new UserDashboard();

                NavigationService.GetNavigationService(this).Navigate(userDashboard);
            });
        }
    }

    #endregion
}
}